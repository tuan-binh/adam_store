package back_end.service.user;

import back_end.exception.CustomException;
import back_end.model.domain.RoleName;
import back_end.model.domain.Roles;
import back_end.model.domain.Users;
import back_end.model.dto.request.UserLogin;
import back_end.model.dto.request.UserRegister;
import back_end.model.dto.request.UserUpdate;
import back_end.model.dto.response.JwtResponse;
import back_end.repository.IUserRepository;
import back_end.security.jwt.JwtProvider;
import back_end.security.user_principle.UserPrinciple;
import back_end.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public JwtResponse login(HttpSession session, UserLogin userLogin) throws CustomException {
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					  new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword())
			);
		} catch (AuthenticationException e) {
			Integer count = (Integer) session.getAttribute("count");
			if (count == null) {
				// lần đầu tiền sai mat khau
				session.setAttribute("count", 1);
			} else {
				// khong phai lan dau tien sai
				if (count == 3) {
					// khoa tai khoan
					Users users = findUserByUserName(userLogin.getEmail());
					users.setStatus(false);
					userRepository.save(users);
					throw new CustomException("your account is blocked");
				} else {
					// thuc hien tang count
					session.setAttribute("count", count + 1);
				}
			}
			throw new CustomException("Username or Password is incorrect");
		}
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		return JwtResponse.builder()
				  .token(jwtProvider.generateToken(userPrinciple))
				  .fullName(userPrinciple.getFullName())
				  .email(userPrinciple.getEmail())
				  .phone(userPrinciple.getPhone())
				  .address(userPrinciple.getAddress())
				  .roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				  .status(userPrinciple.isStatus())
				  .build();
	}
	
	@Override
	public void register(UserRegister userRegister) throws CustomException {
		if (userRepository.existsByEmail(userRegister.getEmail())) {
			throw new CustomException("email is exists");
		}
		Set<Roles> roles = new HashSet<>();
		if (userRegister.getRoles() == null || userRegister.getRoles().isEmpty()) {
			roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
		} else {
			userRegister.getRoles().forEach(
					  role -> {
						  switch (role) {
							  case "admin":
								  try {
									  roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
								  } catch (CustomException e) {
									  throw new RuntimeException(e);
								  }
							  case "user":
								  try {
									  roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
								  } catch (CustomException e) {
									  throw new RuntimeException(e);
								  }
							  default:
								  try {
									  throw new CustomException("role not found");
								  } catch (CustomException e) {
									  throw new RuntimeException(e);
								  }
						  }
					  }
			);
		}
		userRepository.save(Users.builder()
				  .fullName(userRegister.getFullName())
				  .email(userRegister.getEmail())
				  .password(passwordEncoder.encode(userRegister.getPassword()))
				  .roles(roles)
				  .build());
	}
	
	@Override
	public void updateInformation(UserUpdate userUpdate, Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Users user = findUserByUserName(userPrinciple.getUsername());
		user.setAddress(userUpdate.getAddress());
		user.setPhone(userUpdate.getPhone());
		userRepository.save(user);
	}
	
	@Override
	public void changePassword(Optional<String> password, Authentication authentication) throws CustomException {
		if(password.isPresent()) {
			UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
			Users user = findUserByUserName(userPrinciple.getUsername());
			user.setPassword(password.get());
			userRepository.save(user);
		}
		throw new CustomException("you must be has password");
	}
	
	public Users findUserByUserName(String username) throws CustomException {
		Optional<Users> optionalUsers = userRepository.findByEmail(username);
		return optionalUsers.orElseThrow(() -> new CustomException("username not found"));
	}
	
}
