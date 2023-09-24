package back_end.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SizeRequest {
	private String sizeName;
	private boolean status;
}
