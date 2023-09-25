package back_end.model.dto.request;

import lombok.*;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegister {
	private String fullName;
	private String email;
	private String password;
	private List<String> roles = new ArrayList<>();
}
