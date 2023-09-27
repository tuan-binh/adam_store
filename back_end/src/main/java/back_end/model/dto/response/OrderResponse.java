package back_end.model.dto.response;

import back_end.model.domain.Rating;
import back_end.model.domain.TypeDelivery;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
	private Long id;
	
	private TypeDelivery typeDelivery;
	
	private Date timeDelivery;
	
	private String location;
	
	private String phone;
	
	private double total;
	
	private Rating rating;
	
	private boolean status;
}
