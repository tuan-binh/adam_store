package back_end.security.user_principle;

import back_end.model.domain.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPrinciple implements UserDetails {
	private Long id;
	
	private String fullName;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String phone;
	
	private String address;
	
	private List<? extends GrantedAuthority> authorities;
	
	private boolean status;
	
	public static UserDetails build(Users users) {
		return UserPrinciple.builder()
				  .id(users.getId())
				  .fullName(users.getFullName())
				  .email(users.getEmail())
				  .password(users.getPassword())
				  .phone(users.getPhone())
				  .address(users.getAddress())
				  // chuyển đổi từ SetRole về List<GrantedAuthority>
				  .authorities(users.getRoles().stream().map(item -> new SimpleGrantedAuthority(item.getRoleName().name())).collect(Collectors.toList()))
				  .status(users.isStatus())
				  .build();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}
