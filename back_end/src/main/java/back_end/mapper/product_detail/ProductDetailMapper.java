package back_end.mapper.product_detail;

import back_end.exception.CustomException;
import back_end.mapper.IGenericMapper;
import back_end.model.domain.Color;
import back_end.model.domain.Product;
import back_end.model.domain.ProductDetail;
import back_end.model.domain.Size;
import back_end.model.dto.request.ProductDetailRequest;
import back_end.model.dto.response.ProductDetailResponse;
import back_end.repository.IColorRepository;
import back_end.repository.IProductRepository;
import back_end.repository.ISizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("product_detail")
public class ProductDetailMapper implements IGenericMapper<ProductDetail, ProductDetailRequest, ProductDetailResponse> {
	
	@Autowired
	private IProductRepository productRepository;
	@Autowired
	private IColorRepository colorRepository;
	@Autowired
	private ISizeRepository sizeRepository;
	
	@Override
	public ProductDetail toEntity(ProductDetailRequest productDetailRequest) throws CustomException {
		return ProductDetail.builder()
				  .price(productDetailRequest.getPrice())
				  .stock(productDetailRequest.getStock())
				  .product(findProductById(productDetailRequest.getProductId()))
				  .color(findColorById(productDetailRequest.getColorId()))
				  .size(findSizeById(productDetailRequest.getSizeId()))
				  .status(productDetailRequest.isStatus())
				  .build();
	}
	
	@Override
	public ProductDetailResponse toResponse(ProductDetail productDetail) {
		return ProductDetailResponse.builder()
				  .id(productDetail.getId())
				  .price(productDetail.getPrice())
				  .stock(productDetail.getStock())
				  .product(productDetail.getProduct().getProductName())
				  .color(productDetail.getColor().getColorName())
				  .size(productDetail.getSize().getSizeName())
				  .status(productDetail.isStatus())
				  .build();
	}
	
	public Product findProductById(Long productId) throws CustomException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		return optionalProduct.orElseThrow(() -> new CustomException("product not found"));
	}
	
	public Color findColorById(Long colorId) throws CustomException {
		Optional<Color> optionalColor = colorRepository.findById(colorId);
		return optionalColor.orElseThrow(() -> new CustomException("color not found"));
	}
	
	public Size findSizeById(Long sizeId) throws CustomException {
		Optional<Size> optionalSize = sizeRepository.findById(sizeId);
		return optionalSize.orElseThrow(() -> new CustomException("size not found"));
	}
	
}
