package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.UserLogin;
import back_end.model.dto.response.JwtResponse;
import back_end.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(HttpSession session, @RequestBody UserLogin userLogin) throws CustomException {
		return new ResponseEntity<>(userService.login(session,userLogin), HttpStatus.OK);
	}
	
}
