package back_end.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CouponRequest {
	
	private String coupon;
	
	private double percent;
	
	@JsonFormat(timezone = "dd/MM/yyyy")
	private Date startDate;
	
	@JsonFormat(timezone = "dd/MM/yyyy")
	private Date endDate;
	
	private int stock;
	
	private boolean status;
	
}
