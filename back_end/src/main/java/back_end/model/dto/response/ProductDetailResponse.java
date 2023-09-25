package back_end.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetailResponse {
	private Long id;
	
	private double price;
	
	private int stock;
	
	private String product;
	
	private String color;
	
	private String size;
	
	private boolean status;
}
