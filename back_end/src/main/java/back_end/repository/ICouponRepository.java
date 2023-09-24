package back_end.repository;

import back_end.model.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Long> {
}
