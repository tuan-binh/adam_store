package back_end.model.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CouponResponse {
	private Long id;
	
	private String coupon;
	
	private double percent;
	
	private Date startDate;
	
	private Date endDate;
	
	private int stock;
	
	private boolean status;
}
