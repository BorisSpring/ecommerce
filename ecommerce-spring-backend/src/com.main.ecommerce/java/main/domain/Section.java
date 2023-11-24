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
public class Section extends  BaseEntity {

	@JsonIgnore
	@ManyToOne(	cascade = {CascadeType.DETACH,
						   CascadeType.MERGE,
						   CascadeType.PERSIST,
						   CascadeType.REFRESH})
	@JoinColumn(name="category_id", nullable = false)
	private Category category;
	
	
	@OneToMany(mappedBy="section", 	cascade = {
						  CascadeType.DETACH,
						  CascadeType.MERGE,
						  CascadeType.PERSIST,
						  CascadeType.REFRESH})
	private List<SectionItems> items = new ArrayList<>();

	@Column(nullable = false, length = 50)
	private String sectionName;

}
