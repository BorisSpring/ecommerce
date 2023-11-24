package main.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends  BaseEntity {

	@JsonIgnore
	@OneToOne(cascade = {
				CascadeType.REFRESH,
				CascadeType.PERSIST,
				CascadeType.DETACH,
				CascadeType.MERGE})
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
	@OneToMany( fetch = FetchType.EAGER , mappedBy="cart", cascade = {
				CascadeType.DETACH,
				CascadeType.MERGE,
				CascadeType.PERSIST,
				CascadeType.REFRESH})
	private Set<CartItem> cartItems = new HashSet<>();

	@Column(nullable = false)
	private double totalPrice = 0;

	@Column(nullable = false)
	private Integer  totalQuantity = 0;

	@Column(nullable = false)
	private double totalDiscountPrice = 0;
	private double discount = 0;

	
}
