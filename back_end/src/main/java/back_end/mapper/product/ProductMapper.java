package back_end.mapper.product;

import back_end.exception.CustomException;
import back_end.mapper.IGenericMapper;
import back_end.mapper.image.ImageMapper;
import back_end.mapper.product_detail.ProductDetailMapper;
import back_end.model.domain.Category;
import back_end.model.domain.Product;
import back_end.model.dto.request.ProductRequest;
import back_end.model.dto.response.ImageResponse;
import back_end.model.dto.response.ProductDetailResponse;
import back_end.model.dto.response.ProductResponse;
import back_end.repository.ICategoryRepository;
import back_end.repository.IImageRepository;
import back_end.repository.IProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("product")
public class ProductMapper implements IGenericMapper<Product, ProductRequest, ProductResponse> {
	
	@Autowired
	private ICategoryRepository categoryRepository;
	@Autowired
	private IProductDetailRepository productDetailRepository;
	@Autowired
	private IImageRepository iImageRepository;
	@Autowired
	private ProductDetailMapper productDetailMapper;
	@Autowired
	private ImageMapper imageMapper;
	
	@Override
	public Product toEntity(ProductRequest productRequest) throws CustomException {
		return Product.builder()
				  .productName(productRequest.getProductName())
				  .description(productRequest.getDescription())
				  .bought(productRequest.getBought())
				  .category(findCategoryById(productRequest.getCategoryId()))
				  // upload list ảnh và cập nhật ảnh đại diện sản phẩm sẽ ở service
				  .status(productRequest.isStatus())
				  .build();
	}
	
	@Override
	public ProductResponse toResponse(Product product) {
		return ProductResponse.builder()
				  .id(product.getId())
				  .productName(product.getProductName())
				  .description(product.getDescription())
				  .image(product.getImage())
				  .bought(product.getBought())
				  .category(product.getCategory().getCategoryName())
				  .productDetails(getProductDetailByProductId(product.getId()))
				  .images(getImagesByProductId(product.getId()))
				  .status(product.isStatus())
				  .build();
	}
	
	public Category findCategoryById(Long categoryId) throws CustomException {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		return optionalCategory.orElseThrow(() -> new CustomException("category not found"));
	}
	
	public List<ProductDetailResponse> getProductDetailByProductId(Long productId) {
		return productDetailRepository.findAllByProductId(productId).stream()
				  .map(item -> productDetailMapper.toResponse(item))
				  .collect(Collectors.toList());
	}
	
	public List<ImageResponse> getImagesByProductId(Long productId) {
		return iImageRepository.findAllByProductId(productId).stream()
				  .map(item -> imageMapper.toResponse(item))
				  .collect(Collectors.toList());
	}
	
}
