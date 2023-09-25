package back_end.mapper.image;

import back_end.model.domain.ImageProduct;
import back_end.model.dto.response.ImageResponse;
import org.springframework.stereotype.Component;

@Component("image")
public class ImageMapper {
	
	public ImageResponse toResponse(ImageProduct imageProduct) {
		return ImageResponse.builder()
				  .id(imageProduct.getId())
				  .image(imageProduct.getImage())
				  .build();
	}
	
}
