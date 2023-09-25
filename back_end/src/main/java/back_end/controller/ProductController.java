package back_end.controller;

import back_end.model.dto.response.ProductResponse;
import back_end.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getAllProduct(@PageableDefault(page = 0, size = 3) Pageable pageable, @RequestParam Optional<String> search) {
//		return new ResponseEntity<>(productService.)
		return null;
	}
	
}
