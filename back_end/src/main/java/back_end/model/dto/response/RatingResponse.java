package back_end.model.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RatingResponse {
	
	private Long id;
	
	private String username;
	
	private int rate;
	
	private String content;
	
	private Date created;
	
}
