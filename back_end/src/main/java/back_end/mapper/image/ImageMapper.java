package back_end.mapper.image;

import back_end.exception.CustomException;
import back_end.model.domain.ImageProduct;
import back_end.model.domain.Product;
import back_end.model.dto.request.ImageRequest;
import back_end.model.dto.response.ImageResponse;
import back_end.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("image")
public class ImageMapper {
	
	@Autowired
	private IProductRepository productRepository;
	
	public ImageProduct toEntity(ImageRequest imageRequest) throws CustomException {
		return ImageProduct.builder()
				  // upload image
				  .product(findProductById(imageRequest.getProductId()))
				  .build();
	}
	
	public ImageResponse toResponse(ImageProduct imageProduct) {
		return ImageResponse.builder()
				  .id(imageProduct.getId())
				  .image(imageProduct.getImage())
				  .build();
	}
	
	public Product findProductById(Long productId) throws CustomException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		return optionalProduct.orElseThrow(() -> new CustomException("product not found"));
	}
	
}
