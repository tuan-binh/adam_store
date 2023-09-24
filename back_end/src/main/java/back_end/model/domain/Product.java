package back_end.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String productName;
	
	private String description;
	
	private String image;
	
	// số lượng sản phẩm đã bán
	private int bought;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	private boolean status;
	
	
}
