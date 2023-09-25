package back_end.service.color;

import back_end.exception.CustomException;
import back_end.mapper.color.ColorMapper;
import back_end.model.domain.Color;
import back_end.model.dto.request.ColorRequest;
import back_end.model.dto.response.ColorResponse;
import back_end.repository.IColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColorService implements IColorService {
	
	@Autowired
	private IColorRepository colorRepository;
	@Autowired
	private ColorMapper colorMapper;
	
	@Override
	public Page<ColorResponse> findAll(Pageable pageable, Optional<String> colorName) {
		List<ColorResponse> list = colorName.map(s -> colorRepository.findAllByColorNameContainingIgnoreCase(pageable, s).stream()
				  .map(item -> colorMapper.toResponse(item))
				  .collect(Collectors.toList())).orElseGet(() -> colorRepository.findAll(pageable).stream()
				  .map(item -> colorMapper.toResponse(item))
				  .collect(Collectors.toList()));
		return new PageImpl<>(list, pageable, list.size());
	}
	
	@Override
	public ColorResponse findById(Long colorId) throws CustomException {
		Optional<Color> optionalColor = colorRepository.findById(colorId);
		return optionalColor.map(item -> colorMapper.toResponse(item)).orElseThrow(() -> new CustomException("color not found"));
	}
	
	@Override
	public ColorResponse save(ColorRequest colorRequest) {
		return colorMapper.toResponse(colorRepository.save(colorMapper.toEntity(colorRequest)));
	}
	
	@Override
	public ColorResponse update(ColorRequest colorRequest, Long colorId) {
		Color color = colorMapper.toEntity(colorRequest);
		color.setId(colorId);
		return colorMapper.toResponse(colorRepository.save(color));
	}
	
	@Override
	public ColorResponse changeStatus(Long colorId) throws CustomException {
		Optional<Color> optionalColor = colorRepository.findById(colorId);
		if (optionalColor.isPresent()) {
			Color color = optionalColor.get();
			color.setStatus(!color.isStatus());
			return colorMapper.toResponse(colorRepository.save(color));
		}
		throw new CustomException("color not found");
	}
}
