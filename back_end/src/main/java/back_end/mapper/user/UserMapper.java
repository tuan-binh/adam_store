package back_end.mapper.user;

import back_end.exception.CustomException;
import back_end.mapper.IGenericMapper;
import back_end.model.domain.Users;
import back_end.model.dto.request.UserRegister;
import back_end.model.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component("user")
public class UserMapper implements IGenericMapper<Users, UserRegister, UserResponse> {
	
	@Override
	public Users toEntity(UserRegister userRegister) throws CustomException {
		return Users.builder()
				  .fullName(userRegister.getFullName())
				  .email(userRegister.getEmail())
				  .password(userRegister.getPassword())
				  .build();
	}
	
	@Override
	public UserResponse toResponse(Users users) throws CustomException {
		return UserResponse.builder()
				  .id(users.getId())
				  .fullName(users.getFullName())
				  .email(users.getEmail())
				  .phone(users.getPhone())
				  .address(users.getAddress())
				  .roles(users.getRoles())
				  .status(users.isStatus())
				  .build();
	}
}
