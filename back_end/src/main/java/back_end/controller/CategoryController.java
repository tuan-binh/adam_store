package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.CategoryRequest;
import back_end.model.dto.response.CategoryResponse;
import back_end.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<Page<CategoryResponse>> getCategories(@PageableDefault(page = 0, size = 3) Pageable pageable, @RequestParam Optional<String> search) {
		return new ResponseEntity<>(categoryService.findAll(pageable, search), HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) throws CustomException {
		return new ResponseEntity<>(categoryService.findById(categoryId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CategoryResponse> addNewCategory(@ModelAttribute CategoryRequest categoryRequest) {
		return new ResponseEntity<>(categoryService.save(categoryRequest), HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryResponse> updateCategoryById(@ModelAttribute CategoryRequest categoryRequest, @PathVariable Long categoryId) {
		return new ResponseEntity<>(categoryService.update(categoryRequest, categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}/status")
	public ResponseEntity<CategoryResponse> changeStatusCategory(@PathVariable Long categoryId) throws CustomException {
		return new ResponseEntity<>(categoryService.changeStatus(categoryId), HttpStatus.OK);
	}
	
}
