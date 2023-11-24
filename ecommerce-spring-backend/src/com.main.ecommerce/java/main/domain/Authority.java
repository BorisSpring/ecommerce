package main.domain;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Authority extends  BaseEntity {

	@JsonIgnore
	@OneToMany(mappedBy="authority", cascade= { 
						CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	private List<User> users = new ArrayList<>();

	@Column(unique = true, updatable = false, nullable = false)
	private String authority;
	


}
