package back_end.service.product;

import back_end.exception.CustomException;
import back_end.mapper.image.ImageMapper;
import back_end.mapper.product.ProductMapper;
import back_end.mapper.product_detail.ProductDetailMapper;
import back_end.model.domain.ImageProduct;
import back_end.model.domain.Product;
import back_end.model.domain.ProductDetail;
import back_end.model.dto.request.ImageRequest;
import back_end.model.dto.request.ProductDetailRequest;
import back_end.model.dto.request.ProductRequest;
import back_end.model.dto.request.ProductUpdate;
import back_end.model.dto.response.ImageResponse;
import back_end.model.dto.response.ProductResponse;
import back_end.repository.IImageRepository;
import back_end.repository.IProductDetailRepository;
import back_end.repository.IProductRepository;
import back_end.service.upload_aws.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
	
	@Autowired
	private IProductRepository productRepository;
	@Autowired
	private IImageRepository iImageRepository;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ImageMapper imageMapper;
	@Autowired
	private IProductDetailRepository productDetailRepository;
	@Autowired
	private ProductDetailMapper productDetailMapper;
	@Autowired
	private StorageService storageService;
	
	@Override
	public Page<ProductResponse> findAll(Pageable pageable, Optional<String> productName) {
		List<ProductResponse> list = productName.map(s -> productRepository.findAllByProductNameContainingIgnoreCase(s).stream()
				  .map(item -> productMapper.toResponse(item))
				  .collect(Collectors.toList())).orElseGet(() -> productRepository.findAll(pageable).stream()
				  .map(item -> productMapper.toResponse(item))
				  .collect(Collectors.toList()));
		return new PageImpl<>(list, pageable, list.size());
	}
	
	@Override
	public ProductResponse findById(Long productId) throws CustomException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		return optionalProduct.map(item -> productMapper.toResponse(item)).orElseThrow(() -> new CustomException("product not found"));
	}
	
	@Override
	public ProductResponse addNewProduct(ProductRequest productRequest) throws CustomException {
		Product product = productMapper.toEntity(productRequest);
		List<String> urls = new ArrayList<>();
		for (MultipartFile m : productRequest.getFiles()) {
			if (m.isEmpty()) {
				throw new CustomException("You need at least 1 file");
			}
			urls.add(storageService.uploadFile(m));
		}
		product.setImage(urls.get(0));
		Product newProduct = productRepository.save(product);
		for (String url : urls) {
			iImageRepository.save(ImageProduct.builder().image(url).product(newProduct).build());
		}
		return productMapper.toResponse(newProduct);
	}
	
	@Override
	public ProductResponse updateProduct(ProductUpdate productUpdate, Long productId) throws CustomException {
		Product product = productMapper.toEntity(productUpdate);
		product.setId(productId);
		return productMapper.toResponse(productRepository.save(product));
	}
	
	@Override
	public ProductResponse changeStatus(Long productId) throws CustomException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setStatus(!product.isStatus());
			return productMapper.toResponse(productRepository.save(product));
		}
		throw new CustomException("product not found");
	}
	
	@Override
	public ProductResponse changeImageAvatarInProduct(Long imageId, Long productId) throws CustomException {
		ImageProduct imageProduct = findImageById(imageId);
		Product product = findProductById(productId);
		if (Objects.equals(imageProduct.getProduct().getId(), product.getId())) {
			product.setImage(imageProduct.getImage());
			return productMapper.toResponse(productRepository.save(product));
		}
		throw new CustomException("image not in product");
	}
	
	@Override
	public List<ImageResponse> addImageToProduct(ImageRequest imageRequest) throws CustomException {
		Product product = findProductById(imageRequest.getProductId());
		List<String> urls = new ArrayList<>();
		for (MultipartFile m : imageRequest.getFiles()) {
			if (m.isEmpty()) {
				throw new CustomException("You need at least 1 file");
			}
			urls.add(storageService.uploadFile(m));
		}
		for (String url : urls) {
			iImageRepository.save(ImageProduct.builder().image(url).product(product).build());
		}
		return iImageRepository.findAllByProductId(imageRequest.getProductId()).stream()
				  .map(item -> imageMapper.toResponse(item))
				  .collect(Collectors.toList());
	}
	
	@Override
	public List<ImageResponse> removeImageInProduct(Long imageId) throws CustomException {
		ImageProduct imageProduct = findImageById(imageId);
		iImageRepository.deleteById(imageId);
		return iImageRepository.findAllByProductId(imageProduct.getProduct().getId()).stream()
				  .map(item -> imageMapper.toResponse(item))
				  .collect(Collectors.toList());
	}
	
	@Override
	public ProductResponse addNewProductDetail(ProductDetailRequest productDetailRequest) throws CustomException {
		ProductDetail productDetail = productDetailRepository.save(productDetailMapper.toEntity(productDetailRequest));
		return findById(productDetail.getProduct().getId());
	}
	
	public ImageProduct findImageById(Long imageId) throws CustomException {
		Optional<ImageProduct> optionalImageProduct = iImageRepository.findById(imageId);
		return optionalImageProduct.orElseThrow(() -> new CustomException("image not found"));
	}
	
	public Product findProductById(Long productId) throws CustomException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		return optionalProduct.orElseThrow(() -> new CustomException("product not found"));
	}
	
}
