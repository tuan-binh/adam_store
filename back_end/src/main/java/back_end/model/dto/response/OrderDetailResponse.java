package back_end.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailResponse {
	private Long id;
	
	private String productName;
	
	private String url;
	
	private double price;
	
	private int quantity;
}
