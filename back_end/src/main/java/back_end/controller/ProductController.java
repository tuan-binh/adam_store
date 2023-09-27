package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.ImageRequest;
import back_end.model.dto.request.ProductDetailRequest;
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
	public ResponseEntity<ProductResponse> changeImageAvatarInProduct(@PathVariable Long imageId, @PathVariable Long productId) throws CustomException {
		return new ResponseEntity<>(productService.changeImageAvatarInProduct(imageId, productId), HttpStatus.OK);
	}
	
	@PostMapping("/images")
	public ResponseEntity<List<ImageResponse>> addImagesToProduct(@ModelAttribute ImageRequest imageRequest) throws CustomException {
		return new ResponseEntity<>(productService.addImageToProduct(imageRequest), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{imageId}/image")
	public ResponseEntity<List<ImageResponse>> removeImageInProduct(@PathVariable Long imageId) throws CustomException {
		return new ResponseEntity<>(productService.removeImageInProduct(imageId), HttpStatus.OK);
	}
	
	@PostMapping("/productDetail")
	public ResponseEntity<ProductResponse> addNewProductDetail(@RequestBody ProductDetailRequest productDetailRequest) throws CustomException {
		return new ResponseEntity<>(productService.addNewProductDetail(productDetailRequest), HttpStatus.CREATED);
	}
	
	@PutMapping("/productDetail/{productDetailId}")
	public ResponseEntity<ProductResponse> updateProductDetail(@RequestParam String price, @RequestParam String stock, @PathVariable Long productDetailId) throws CustomException {
		return new ResponseEntity<>(productService.updateProductDetail(price, stock, productDetailId), HttpStatus.OK);
	}
	
}
