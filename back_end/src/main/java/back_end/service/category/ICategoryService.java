package back_end.service.category;

import back_end.exception.CustomException;
import back_end.model.dto.request.CategoryRequest;
import back_end.model.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryService {
	
	Page<CategoryResponse> findAll(Pageable pageable, Optional<String> categoryName);
	
	CategoryResponse findById(Long categoryId) throws CustomException;
	
	CategoryResponse save(CategoryRequest categoryRequest);
	
	CategoryResponse update(CategoryRequest categoryRequest, Long categoryId);
	
	CategoryResponse changeStatus(Long categoryId) throws CustomException;
	
}
