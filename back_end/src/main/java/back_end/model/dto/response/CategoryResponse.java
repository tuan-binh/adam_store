package back_end.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {
	private Long id;
	
	private String image;
	
	private String categoryName;
	
	private boolean status;
}
