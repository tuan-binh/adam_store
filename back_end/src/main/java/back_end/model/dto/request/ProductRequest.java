package back_end.model.dto.request;

import back_end.model.domain.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
	private String productName;
	
	private String description;
	
	private int bought;
	
	private Long categoryId;
	
	private List<MultipartFile> files;
	
	private boolean status;
}
