package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.ColorRequest;
import back_end.model.dto.response.ColorResponse;
import back_end.service.color.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/color")
@CrossOrigin("*")
public class ColorController {
	
	@Autowired
	private IColorService colorService;
	
	@GetMapping
	public ResponseEntity<Page<ColorResponse>> getAllColor(@PageableDefault(page = 0, size = 3) Pageable pageable, @RequestParam Optional<String> search) {
		return new ResponseEntity<>(colorService.findAll(pageable, search), HttpStatus.OK);
	}
	
	@GetMapping("/{colorId}")
	public ResponseEntity<ColorResponse> getColorById(@PathVariable Long colorId) throws CustomException {
		return new ResponseEntity<>(colorService.findById(colorId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ColorResponse> addNewColor(@RequestBody ColorRequest colorRequest) {
		return new ResponseEntity<>(colorService.save(colorRequest), HttpStatus.CREATED);
	}
	
	@PutMapping("/{colorId}")
	public ResponseEntity<ColorResponse> updateColor(@RequestBody ColorRequest colorRequest, @PathVariable Long colorId) {
		return new ResponseEntity<>(colorService.update(colorRequest, colorId), HttpStatus.OK);
	}
	
	@GetMapping("/{colorId}/status")
	public ResponseEntity<ColorResponse> changeStatusColor(@PathVariable Long colorId) throws CustomException {
		return new ResponseEntity<>(colorService.changeStatus(colorId), HttpStatus.OK);
	}
	
}
