package back_end.mapper;

import back_end.exception.CustomException;

public interface IGenericMapper<T, K, L> {
	T toEntity(K k) throws CustomException;
	L toResponse(T t) throws CustomException;
}
