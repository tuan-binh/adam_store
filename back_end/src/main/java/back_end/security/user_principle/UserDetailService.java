package back_end.security.user_principle;

import back_end.model.domain.Users;
import back_end.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = findByUsername(username);
		return UserPrinciple.build(users);
	}
	
	public Users findByUsername(String username) {
		Optional<Users> optionalUsers = userRepository.findByEmail(username);
		return optionalUsers.orElseThrow(() -> new RuntimeException("email not found"));
	}
	
}
