package back_end.mapper;

public interface IGenericMapper<T, K, L> {
	T toEntity(K k);
	L toResponse(T t);
}
