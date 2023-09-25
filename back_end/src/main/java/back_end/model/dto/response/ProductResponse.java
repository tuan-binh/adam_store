package back_end.model.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
	private Long id;
	
	private String productName;
	
	private String description;
	
	private String image;
	
	private int bought;
	
	private String category;
	
	private List<ProductDetailResponse> productDetails;
	
	private List<ImageResponse> images;
	
	private boolean status;
}
