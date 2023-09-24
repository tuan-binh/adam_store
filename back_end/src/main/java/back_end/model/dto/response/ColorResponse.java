package back_end.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ColorResponse {
	private Long id;
	
	private String colorName;
	
	private boolean status;
}
