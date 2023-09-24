package back_end.repository;

import back_end.model.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
