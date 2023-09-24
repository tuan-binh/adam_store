package back_end.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private double price;
	
	private int stock;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;
	
	@ManyToOne
	@JoinColumn(name = "size_id")
	private Size size;
	
	private boolean status;
	
}
