package back_end.model.dto.request;

import lombok.*;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegister {
	private String fullName;
	private String email;
	private String password;
}
