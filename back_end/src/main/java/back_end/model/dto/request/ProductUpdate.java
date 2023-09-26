package back_end.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductUpdate {
	private String productName;
	
	private String description;
	
	private int bought;
	
	private Long categoryId;
	
	private boolean status;
}
