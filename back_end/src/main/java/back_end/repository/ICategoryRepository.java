package back_end.repository;

import back_end.model.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
	
	Page<Category> findAllByCategoryNameContainingIgnoreCase(Pageable pageable, String categoryName);
	
}
