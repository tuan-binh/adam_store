package back_end.model.dto.request;

import back_end.model.domain.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageRequest {
	private List<MultipartFile> files;
	private Long productId;
}
