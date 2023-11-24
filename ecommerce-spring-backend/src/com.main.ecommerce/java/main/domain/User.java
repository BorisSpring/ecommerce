package main.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="users")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User  {


	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(nullable = false, updatable = false, length = 36)
	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID id;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime lastModifiedDate;

	@Column(length = 50, nullable = false)
	private String firstName;

	@Column(length = 50, nullable = false)
	private String lastName;

	@Column(nullable = false)
	private LocalDate birth;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 150)
	private String password;
	
	@OneToMany(mappedBy="user", cascade= { 
						CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	private List<Adress> adress = new ArrayList<>();
	
	
	@ManyToOne(cascade= {
						CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	@JoinColumn(name="authority_id", nullable = false)
	private Authority authority;
	
	
	@OneToMany(mappedBy="user",  cascade= { 
						CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	private List<MobileNumber> mobileNumbers = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade = {CascadeType.DETACH,
							CascadeType.MERGE,
							CascadeType.PERSIST,
							CascadeType.REFRESH})
	private List<Rating> raitings = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="user" ,
			cascade= {CascadeType.DETACH,
					  CascadeType.MERGE,
					  CascadeType.PERSIST,
					  CascadeType.REFRESH})
	private List<Review> reviews = new ArrayList<>();
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	private List<Order> orders = new ArrayList<>();
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL)
	private Cart cart;

	@JsonIgnore
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<CartItem> cartItems = new ArrayList<>();

}
