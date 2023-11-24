package main.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Orders")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends  BaseEntity {

	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	@JoinColumn(name="user_id", nullable = false)
	private User user;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="adres_id", unique=false, nullable = false)
	private Adress adress;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="mobile_id", nullable = false)
	private MobileNumber mobileNumber;

	@OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Column(nullable = false)
	private LocalDateTime orderTime;

	@Column(insertable = false)
	private LocalDateTime deliveredTime;

	@Column(nullable = false)
	private double totalPrice;

	@Column(nullable = false)
	private double totalDiscountedPrice;

	private double discount = 0;

	@Column(nullable = false)
	private Integer totalItem = 0;

	@Enumerated(EnumType.STRING)
	private OrderStatusEnum orderStatus;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;


	

}
