package back_end.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private double price;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders orders;
	
	@ManyToOne
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;
	
}
