package back_end.security.jwt;

import back_end.security.user_principle.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
	
	private final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
	
	
	@Value("${jwt.secret-key}")
	private String SECRET_KEY;
	
	@Value("${jwt.expired}")
	private Long EXPIRED;
	
	public String generateToken(UserPrinciple userPrinciple) {
		// set username ( email ) vào phần subject
		return Jwts.builder().setSubject(userPrinciple.getEmail())
				  .setIssuedAt(new Date()) // set thời gian sống của token
				  .setExpiration(new Date(new Date().getTime() + EXPIRED)) // set thời gian chết của token
				  .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // chữ ký và mã hóa chuỗi bí mật
				  .compact();
	}
	
	// thực hiện kiển tra token có hợp lệ không
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			logger.error("Failed -> Expired Token Message {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Failed -> Unsupported Token Message {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Failed -> Invalid Format Token Message {}", e.getMessage());
		} catch (SignatureException e) {
			logger.error("Failed -> Invalid Signature Token Message {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Failed -> Claims Empty Token Message {}", e.getMessage());
		}
		return false;
	}
	
	public String getUserNameFromToken(String token) {
		// lấy username ra ở subject khi lúc mình set vào ở token
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}
	
}
