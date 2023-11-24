package main.domain;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends  BaseEntity {
	@OneToMany(mappedBy="category", cascade= CascadeType.ALL)
	private List<Section> sections;

	@Column(nullable = false, length = 50)
	private String categoryName;
	
}
