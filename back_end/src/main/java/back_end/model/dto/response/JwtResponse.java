package back_end.model.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
	private String token;
	private final String type = "Bearer";
	private String fullName;
	private String email;
	private String phone;
	private String address;
	private List<String> roles = new ArrayList<>();
	private boolean status;
}
