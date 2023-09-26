package back_end.repository;

import back_end.model.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	List<OrderDetail> findAllByOrdersId(Long orderId);
	
	Optional<OrderDetail> findByOrdersIdAndProductDetailId(Long orderId, Long productDetailId);
	
}
