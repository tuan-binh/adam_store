package back_end.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {
	private String categoryName;
	private MultipartFile file;
	private boolean status;
}
