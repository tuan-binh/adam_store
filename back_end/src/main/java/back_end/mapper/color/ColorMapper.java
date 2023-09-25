package back_end.mapper.color;

import back_end.mapper.IGenericMapper;
import back_end.model.domain.Color;
import back_end.model.dto.request.ColorRequest;
import back_end.model.dto.response.ColorResponse;
import org.springframework.stereotype.Component;

@Component("color")
public class ColorMapper implements IGenericMapper<Color, ColorRequest, ColorResponse> {
	@Override
	public Color toEntity(ColorRequest colorRequest) {
		return Color.builder()
				  .colorName(colorRequest.getColorName())
				  .status(colorRequest.isStatus())
				  .build();
	}
	
	@Override
	public ColorResponse toResponse(Color color) {
		return ColorResponse.builder()
				  .id(color.getId())
				  .colorName(color.getColorName())
				  .status(color.isStatus())
				  .build();
	}
}
