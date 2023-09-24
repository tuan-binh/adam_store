package back_end.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RatingRequest {
	private int rate;
	
	private String content;
	
	@JsonFormat(timezone = "dd/MM/yyyy")
	private Date created;
}
