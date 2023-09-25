package back_end.repository;

import back_end.model.domain.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<ImageProduct, Long> {
	List<ImageProduct> findAllByProductId(Long productId);
}
