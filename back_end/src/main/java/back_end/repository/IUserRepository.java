package back_end.repository;

import back_end.model.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
	
	boolean existsByEmail(String email);
	
	boolean existsByPhone(String phone);
	
	Optional<Users> findByEmail(String email);
	
}
