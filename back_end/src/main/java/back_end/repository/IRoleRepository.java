package back_end.repository;

import back_end.model.domain.RoleName;
import back_end.model.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Roles, Long> {
	
	Optional<Roles> findByRoleName(RoleName roleName);
	
}
