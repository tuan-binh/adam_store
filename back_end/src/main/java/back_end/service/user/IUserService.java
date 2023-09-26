package back_end.service.user;

import back_end.exception.CustomException;
import back_end.model.dto.request.UserLogin;
import back_end.model.dto.request.UserRegister;
import back_end.model.dto.request.UserUpdate;
import back_end.model.dto.response.JwtResponse;
import back_end.model.dto.response.ProductResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface IUserService {
	
	JwtResponse login(HttpSession session, UserLogin userLogin) throws CustomException;
	
	void register(UserRegister userRegister) throws CustomException;
	
	List<ProductResponse> getFavourite(Authentication authentication) throws CustomException;
	
	List<ProductResponse> addProductToFavourite(Long productId, Authentication authentication) throws CustomException;
	
	List<ProductResponse> removeProductInFavourite(Long productId, Authentication authentication) throws CustomException;
	
	void updateInformation(UserUpdate userUpdate, Authentication authentication) throws CustomException;
	
	void changePassword(Optional<String> password, Authentication authentication) throws CustomException;
	
}
