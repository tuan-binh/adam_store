package back_end.service.role;

import back_end.exception.CustomException;
import back_end.model.domain.RoleName;
import back_end.model.domain.Roles;
import back_end.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Override
	public Roles findByRoleName(RoleName roleName) throws CustomException {
		Optional<Roles> optionalRoles = roleRepository.findByRoleName(roleName);
		return optionalRoles.orElseThrow(() -> new CustomException("role not found"));
	}
}
