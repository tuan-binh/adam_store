package back_end.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckoutRequest {
	private Long couponId;
	private String address;
	private String phone;
}
