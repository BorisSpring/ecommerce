package main.domain;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adress  extends  BaseEntity{

	@JsonIgnore
	@ManyToOne( cascade= { 
						CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	@JsonIgnore
	@OneToMany(mappedBy="adress", cascade = {
						CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	private List<Order> orders = new ArrayList<>();

	@Column(nullable = false)
	private String adress;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String country;

	@Column(nullable = false)
	private String postalCode;

	@Column(nullable = false)
	private String state;
	



	
}
