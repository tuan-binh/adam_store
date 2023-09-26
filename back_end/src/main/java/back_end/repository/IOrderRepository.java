package back_end.repository;

import back_end.model.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Long> {
	
	Optional<Orders> findByRatingId(Long ratingId);
	
	Page<Orders> findAllByPhoneContainingIgnoreCaseAndStatus(Pageable pageable,String phone,boolean status);
	
	Page<Orders> findAllByUsersIdAndStatus(Pageable pageable,Long userId,boolean status);
	
	Optional<Orders> findByUsersIdAndStatus(Long userId,boolean status);
	
	Optional<Orders> findByIdAndUsersIdAndStatus(Long orderId,Long userId,boolean status);
	
}
