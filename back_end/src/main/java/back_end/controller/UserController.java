package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.UserUpdate;
import back_end.model.dto.response.ProductResponse;
import back_end.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/favourite")
	public ResponseEntity<List<ProductResponse>> getFavourite(Authentication authentication) {
		return null;
	}
	
	@PostMapping("/{productId}/favourite")
	public ResponseEntity<List<ProductResponse>> addProductToFavourite(@PathVariable Long productId, Authentication authentication) {
		return null;
	}
	
	@DeleteMapping("/{productId}/favourite")
	public ResponseEntity<List<ProductResponse>> removeProductInFavourite(@PathVariable Long productId, Authentication authentication) {
		return null;
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateInformation(@RequestBody UserUpdate userUpdate, Authentication authentication) throws CustomException {
		userService.updateInformation(userUpdate, authentication);
		return new ResponseEntity<>("update your information successfully", HttpStatus.OK);
	}
	
	@PutMapping("/password")
	public ResponseEntity<String> changePassword(@RequestParam Optional<String> password, Authentication authentication) throws CustomException {
		userService.changePassword(password, authentication);
		return new ResponseEntity<>("update password successfully", HttpStatus.OK);
	}
	
}
