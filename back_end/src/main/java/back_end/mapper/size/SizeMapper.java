package back_end.mapper.size;

import back_end.exception.CustomException;
import back_end.mapper.IGenericMapper;
import back_end.model.domain.Size;
import back_end.model.dto.request.SizeRequest;
import back_end.model.dto.response.SizeResponse;
import org.springframework.stereotype.Component;

@Component("size")
public class SizeMapper implements IGenericMapper<Size, SizeRequest, SizeResponse> {
	
	@Override
	public Size toEntity(SizeRequest sizeRequest) {
		return Size.builder()
				  .sizeName(sizeRequest.getSizeName())
				  .status(sizeRequest.isStatus())
				  .build();
	}
	
	@Override
	public SizeResponse toResponse(Size size) {
		return SizeResponse.builder()
				  .id(size.getId())
				  .sizeName(size.getSizeName())
				  .status(size.isStatus())
				  .build();
	}
}
