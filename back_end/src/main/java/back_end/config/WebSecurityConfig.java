package back_end.config;

import back_end.security.jwt.CustomAccessDeniedHandler;
import back_end.security.jwt.JwtEntryPoint;
import back_end.security.jwt.JwtTokenFilter;
import back_end.security.user_principle.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private JwtEntryPoint jwtEntryPoint;
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	// dùng để mã hóa password
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				  .authorizeRequests()
				  .antMatchers("/auth/**").permitAll()
				  .anyRequest().authenticated()
				  .and()
				  .exceptionHandling().authenticationEntryPoint(jwtEntryPoint).accessDeniedHandler(customAccessDeniedHandler)
				  .and()
				  .sessionManagement()
				  .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
