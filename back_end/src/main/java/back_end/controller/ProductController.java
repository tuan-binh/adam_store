package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.ProductRequest;
import back_end.model.dto.request.ProductUpdate;
import back_end.model.dto.response.ImageResponse;
import back_end.model.dto.response.ProductResponse;
import back_end.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getAllProduct(@PageableDefault(page = 0, size = 3) Pageable pageable, @RequestParam Optional<String> search) {
		return new ResponseEntity<>(productService.findAll(pageable, search), HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) throws CustomException {
		return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ProductResponse> addNewProduct(@ModelAttribute ProductRequest productRequest) throws CustomException {
		return new ResponseEntity<>(productService.addNewProduct(productRequest), HttpStatus.CREATED);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductUpdate productUpdate, @PathVariable Long productId) throws CustomException {
		return new ResponseEntity<>(productService.updateProduct(productUpdate, productId), HttpStatus.OK);
	}
	
	@GetMapping("/{productId}/status")
	public ResponseEntity<ProductResponse> changeStatus(@PathVariable Long productId) throws CustomException {
		return new ResponseEntity<>(productService.changeStatus(productId), HttpStatus.OK);
	}
	
	@PutMapping("image/{imageId}/product/{productId}")
	public ResponseEntity<ProductResponse> changeImageAvatarInProduct(@PathVariable Long imageId, @PathVariable Long productId) {
		return null;
	}
	
	@PostMapping("/images")
	public ResponseEntity<List<ImageResponse>> addImagesToProduct() {
		return null;
	}
	
	@DeleteMapping("/{imageId}/image")
	public ResponseEntity<List<ImageResponse>> removeImageInProduct(@PathVariable Long imageId) {
		return null;
	}
	
	
	
}
