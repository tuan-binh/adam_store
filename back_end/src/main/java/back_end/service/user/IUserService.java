package back_end.service.user;

import back_end.exception.CustomException;
import back_end.model.dto.request.UserLogin;
import back_end.model.dto.response.JwtResponse;

import javax.servlet.http.HttpSession;

public interface IUserService {
	
	public JwtResponse login(HttpSession session, UserLogin userLogin) throws CustomException;
	
}
