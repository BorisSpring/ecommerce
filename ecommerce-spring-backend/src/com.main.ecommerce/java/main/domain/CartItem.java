package main.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends  BaseEntity {

	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="cart_id", nullable = false)
	private Cart cart;
	
	@ManyToOne( cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="product_id", nullable = false)
	private Product product;
	
	@JsonIgnore
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private String size;

	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private double discountPrice;
	@Column(nullable = false)
	private double discount;

	@Column(nullable = false)
	private double totalPrice;

	
}
