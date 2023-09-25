package back_end.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SizeResponse {
	
	private Long id;
	
	private String sizeName;
	
	private boolean status;
	
}
