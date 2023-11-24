package main.domain;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionItems extends  BaseEntity {

	@Column(nullable = false, length = 50)
	private String itemName;
	
	@JsonIgnore
	@ManyToOne(	cascade = {CascadeType.DETACH,
							CascadeType.MERGE,
							CascadeType.PERSIST,
							CascadeType.REFRESH})
	@JoinColumn(name="section_id", nullable = false)
	private Section section;

	@JsonIgnore
	@OneToMany(mappedBy="sectionItem",
				cascade= {CascadeType.DETACH,
							CascadeType.MERGE,
							CascadeType.PERSIST,
							CascadeType.REFRESH})
	private List<Product> product = new ArrayList<>();

}
