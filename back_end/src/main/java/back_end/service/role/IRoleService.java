package back_end.service.role;

import back_end.exception.CustomException;
import back_end.model.domain.RoleName;
import back_end.model.domain.Roles;

public interface IRoleService {
	
	Roles findByRoleName(RoleName roleName) throws CustomException;
	
}
