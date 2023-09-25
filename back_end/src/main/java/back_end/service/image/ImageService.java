package back_end.service.image;

import back_end.exception.CustomException;
import back_end.mapper.image.ImageMapper;
import back_end.model.domain.ImageProduct;
import back_end.model.dto.response.ImageResponse;
import back_end.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService implements IImageService {
	
	@Autowired
	private IImageRepository iImageRepository;
	@Autowired
	private ImageMapper imageMapper;
	
	@Override
	public List<ImageResponse> findAllByProductId(Long productId) {
		return iImageRepository.findAllByProductId(productId).stream()
				  .map(item -> imageMapper.toResponse(item))
				  .collect(Collectors.toList());
	}
	
	@Override
	public ImageResponse findById(Long imageId) throws CustomException {
		Optional<ImageProduct> optionalImageProduct = iImageRepository.findById(imageId);
		return optionalImageProduct.map(item -> imageMapper.toResponse(item)).orElseThrow(() -> new CustomException("image not found"));
	}
	
	@Override
	public ImageResponse save(ImageProduct imageProduct) {
		return imageMapper.toResponse(iImageRepository.save(imageProduct));
	}
	
	@Override
	public ImageResponse update(ImageProduct imageProduct, Long imageId) {
		imageProduct.setId(imageId);
		return imageMapper.toResponse(iImageRepository.save(imageProduct));
	}
	
	@Override
	public ImageResponse delete(Long imageId) throws CustomException {
		Optional<ImageProduct> optionalImageProduct = iImageRepository.findById(imageId);
		if (optionalImageProduct.isPresent()) {
			iImageRepository.deleteById(imageId);
			return imageMapper.toResponse(optionalImageProduct.get());
		}
		throw new CustomException("image not found");
	}
}
