package back_end.model.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetailRequest {
	
	private double price;
	
	private int stock;
	
	private Long productId;
	
	private Long colorId;
	
	private Long sizeId;
	
	private boolean status;
}
