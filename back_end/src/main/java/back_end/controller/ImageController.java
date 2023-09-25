package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.domain.ImageProduct;
import back_end.model.dto.response.ImageResponse;
import back_end.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")
@CrossOrigin("*")
public class ImageController {
	
	@Autowired
	private IImageService iImageService;
	
	@GetMapping("/{productId}")
	public ResponseEntity<List<ImageResponse>> getImageByProductId(@PathVariable Long productId) {
		return new ResponseEntity<>(iImageService.findAllByProductId(productId), HttpStatus.OK);
	}
	
	@GetMapping("/{imageId}")
	public ResponseEntity<ImageResponse> getImageById(@PathVariable Long imageId) throws CustomException {
		return new ResponseEntity<>(iImageService.findById(imageId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ImageResponse> addNewImage(@ModelAttribute ImageProduct imageProduct) {
		return new ResponseEntity<>(iImageService.save(imageProduct), HttpStatus.CREATED);
	}
	
	@PutMapping("/{imageId}")
	public ResponseEntity<ImageResponse> updateImage(@ModelAttribute ImageProduct imageProduct, @PathVariable Long imageId) {
		return new ResponseEntity<>(iImageService.update(imageProduct, imageId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{imageId}")
	public ResponseEntity<ImageResponse> deleteImageById(@PathVariable Long imageId) throws CustomException {
		return new ResponseEntity<>(iImageService.delete(imageId), HttpStatus.OK);
	}
	
}
