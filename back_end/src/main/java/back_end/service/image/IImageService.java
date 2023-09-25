package back_end.service.image;

import back_end.exception.CustomException;
import back_end.model.domain.ImageProduct;
import back_end.model.dto.response.ImageResponse;

import java.util.List;

public interface IImageService {
	
	List<ImageResponse> findAllByProductId(Long productId);
	
	ImageResponse findById(Long imageId) throws CustomException;
	
	ImageResponse save(ImageProduct imageProduct);
	
	ImageResponse update(ImageProduct imageProduct, Long imageId);
	
	ImageResponse delete(Long imageId) throws CustomException;
	
}
