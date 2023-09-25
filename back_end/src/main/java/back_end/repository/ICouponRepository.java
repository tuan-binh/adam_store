package back_end.repository;

import back_end.model.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Long> {
	
	Page<Coupon> findAllByCouponContainingIgnoreCase(Pageable pageable, String coupon);
	
}
