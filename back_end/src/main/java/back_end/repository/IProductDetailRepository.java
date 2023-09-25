package back_end.repository;

import back_end.model.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
	
	List<ProductDetail> findAllByProductId(Long productId);
	
}
