package back_end.service.color;

import back_end.exception.CustomException;
import back_end.model.dto.request.ColorRequest;
import back_end.model.dto.response.ColorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IColorService {
	
	Page<ColorResponse> findAll(Pageable pageable, Optional<String> colorName);
	
	ColorResponse findById(Long colorId) throws CustomException;
	
	ColorResponse save(ColorRequest colorRequest);
	
	ColorResponse update(ColorRequest colorRequest, Long colorId);
	
	ColorResponse changeStatus(Long colorId) throws CustomException;
	
}
