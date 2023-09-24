package back_end.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ColorRequest {
	private String colorName;
	private boolean status;
}
