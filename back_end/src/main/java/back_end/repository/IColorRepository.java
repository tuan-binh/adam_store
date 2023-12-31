package back_end.repository;

import back_end.model.domain.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IColorRepository extends JpaRepository<Color, Long> {
	
	Page<Color> findAllByColorNameContainingIgnoreCase(Pageable pageable, String colorName);
	
}
