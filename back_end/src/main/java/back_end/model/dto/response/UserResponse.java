package back_end.model.dto.response;

import back_end.model.domain.Roles;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
	private Long id;
	
	private String fullName;
	
	private String email;
	
	private String phone;
	
	private String address;
	
	private Set<Roles> roles = new HashSet<>();
	
	private boolean status;
}
