package back_end.service.product;

import back_end.exception.CustomException;
import back_end.model.dto.request.ImageRequest;
import back_end.model.dto.request.ProductRequest;
import back_end.model.dto.request.ProductUpdate;
import back_end.model.dto.response.ImageResponse;
import back_end.model.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
	
	Page<ProductResponse> findAll(Pageable pageable, Optional<String> productName);
	
	ProductResponse findById(Long productId) throws CustomException;
	
	ProductResponse addNewProduct(ProductRequest productRequest) throws CustomException;
	
	ProductResponse updateProduct(ProductUpdate productUpdate, Long productId) throws CustomException;
	
	ProductResponse changeStatus(Long productId) throws CustomException;
	
	ProductResponse changeImageAvatarInProduct(Long imageId, Long productId) throws CustomException;
	
	List<ImageResponse> addImageToProduct(ImageRequest imageRequest) throws CustomException;
	
	List<ImageResponse> removeImageInProduct(Long imageId) throws CustomException;
	
}
