package back_end.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdate {
	private String phone;
	private String address;
}
