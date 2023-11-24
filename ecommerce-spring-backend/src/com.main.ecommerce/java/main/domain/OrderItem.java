package main.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends  BaseEntity {


	@JsonIgnore
	@ManyToOne(cascade= {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="order_id", nullable = false)
	public Order order;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private double discountPrice;

	@Column(nullable = false)
	private String size;

	@Column(nullable = false, length = 36, updatable = false)
	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID userId;
	private LocalDateTime deliveryDate;

}
