package back_end.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TypeDelivery typeDelivery;
	
	private Date timeDelivery;
	
	private String location;
	
	private String phone;
	
	private double total;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;
	
	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	
	@OneToOne
	@JoinColumn(name = "rate_id")
	private Rating rating;
	
	private boolean status;
	
}
