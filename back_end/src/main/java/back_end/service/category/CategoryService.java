package back_end.service.category;

import back_end.exception.CustomException;
import back_end.mapper.category.CategoryMapper;
import back_end.model.domain.Category;
import back_end.model.dto.request.CategoryRequest;
import back_end.model.dto.response.CategoryResponse;
import back_end.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private ICategoryRepository categoryRepository;
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public Page<CategoryResponse> findAll(Pageable pageable, Optional<String> categoryName) {
		List<CategoryResponse> list = categoryName.map(s -> categoryRepository.findAllByCategoryNameContainingIgnoreCase(pageable, s).stream()
				  .map(item -> categoryMapper.toResponse(item))
				  .collect(Collectors.toList())).orElseGet(() -> categoryRepository.findAll(pageable).stream()
				  .map(item -> categoryMapper.toResponse(item))
				  .collect(Collectors.toList()));
		return new PageImpl<>(list, pageable, list.size());
	}
	
	@Override
	public CategoryResponse findById(Long categoryId) throws CustomException {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		return optionalCategory.map(item -> categoryMapper.toResponse(item))
				  .orElseThrow(() -> new CustomException("category not found"));
	}
	
	@Override
	public CategoryResponse save(CategoryRequest categoryRequest) {
		return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(categoryRequest)));
	}
	
	@Override
	public CategoryResponse update(CategoryRequest categoryRequest, Long categoryId) {
		Category category = categoryMapper.toEntity(categoryRequest);
		category.setId(categoryId);
		return categoryMapper.toResponse(categoryRepository.save(category));
	}
	
	@Override
	public CategoryResponse changeStatus(Long categoryId) throws CustomException {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if (optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			category.setStatus(!category.isStatus());
			return categoryMapper.toResponse(categoryRepository.save(category));
		}
		throw new CustomException("category not found");
	}
	
}
