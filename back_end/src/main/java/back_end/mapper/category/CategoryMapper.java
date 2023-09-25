package back_end.mapper.category;

import back_end.mapper.IGenericMapper;
import back_end.model.domain.Category;
import back_end.model.dto.request.CategoryRequest;
import back_end.model.dto.response.CategoryResponse;
import back_end.service.upload_aws.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("category")
public class CategoryMapper implements IGenericMapper<Category, CategoryRequest, CategoryResponse> {
	
	@Autowired
	private StorageService storageService;
	
	@Override
	public Category toEntity(CategoryRequest categoryRequest) {
		return Category.builder()
				  .categoryName(categoryRequest.getCategoryName())
				  .image(storageService.uploadFile(categoryRequest.getFile()))
				  .status(categoryRequest.isStatus())
				  .build();
	}
	
	@Override
	public CategoryResponse toResponse(Category category) {
		return CategoryResponse.builder()
				  .id(category.getId())
				  .image(category.getImage())
				  .categoryName(category.getCategoryName())
				  .status(category.isStatus())
				  .build();
	}
}
