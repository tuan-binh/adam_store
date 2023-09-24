package back_end.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
}
