package main.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product  extends  BaseEntity implements Serializable {


	@ElementCollection
	@CollectionTable(name="product_images_url", joinColumns = @JoinColumn(name="image_id"))
	@Column(name="images")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> imageUrl = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name="product_colors", joinColumns =@JoinColumn(name="color_id"))
	@Column(name="colors")
//	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> colors = new ArrayList<>();
	
	@ManyToOne(cascade = {
					CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH})
	@JoinColumn(name="sectionItem_id", nullable = false)
	private SectionItems sectionItem;
	
	@ElementCollection
	@CollectionTable(name="product_highlights" , joinColumns= @JoinColumn(name="highlight_id"))
	@Column(name="highlights")
//	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> highlights = new ArrayList<>();

	@Column(nullable = false, length = 50)
	private String name;

	@ElementCollection
	@CollectionTable(name="products_sizes", joinColumns = @JoinColumn(name = "size_id"))
	@Column(name="size")
	private Set<Size> sizes= new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rating> raitings = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="product", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="product", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<CartItem> cartItems = new ArrayList<>();

	@Column(nullable = false)
	private double price;

	private double discountPrice;
	private Integer discountPrecent;
	private int quantity = 0;
	private int numOfRating = 0;
	private int numOfRewies = 0;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, length = 1000)
	private String description;

	@Column(nullable = false, length = 50)
	private String brand;


	
}
