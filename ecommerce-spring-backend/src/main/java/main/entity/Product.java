package main.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@ElementCollection
	@CollectionTable(name="product_images_url", joinColumns = @JoinColumn(name="id"))
	@Column(name="images")
	private List<String> imageUrl = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name="product_colors", joinColumns =@JoinColumn(name="id"))
	@Column(name="colors")
	private List<String> colors = new ArrayList<>();
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="sectionItem_id")
	private SectionItems sectionItem;
	
	@ElementCollection
	@CollectionTable(name="product_highlights" , joinColumns= @JoinColumn(name="id"))
	@Column(name="highlights")
	private List<String> highlights = new ArrayList<>();
	
	private String name;
	
	@Embedded
	@ElementCollection
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
	
	
	private int price;
	private int discountPrice;
	private int discountPrecent;
	private int quantity;
	private int numOfRating;
	private int numOfRewies;
	private String title;
	private String description;
	private String brand;
	private LocalDateTime createdAt;

	
	public Product() {

	}
	
	
	
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	
	public List<Rating> getRaitings() {
		return raitings;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}

	public int getId() {
		return id;
	}
	

	public List<String> getHighlights() {
		return highlights;
	}

	public void setHighlights(List<String> highlights) {
		this.highlights = highlights;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getDiscountPrecent() {
		return discountPrecent;
	}

	public void setDiscountPrecent(int discountPrecent) {
		this.discountPrecent = discountPrecent;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getNumOfRating() {
		return numOfRating;
	}

	public void setNumOfRating(int numOfRating) {
		this.numOfRating = numOfRating;
	}

	public int getNumOfRewies() {
		return numOfRewies;
	}

	public void setNumOfRewies(int numOfRewies) {
		this.numOfRewies = numOfRewies;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public SectionItems getSectionItem() {
		return sectionItem;
	}

	public void setSectionItem(SectionItems sectionItem) {
		this.sectionItem = sectionItem;
	}

	public Set<Size> getSizes() {
		return sizes;
	}

	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}

	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public void setRaitings(List<Rating> raitings) {
		this.raitings = raitings;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", sectionItem=" + sectionItem + ", highlights=" + highlights + ", name=" + name
				+ ", sizes=" + sizes + ", price=" + price + ", discountPrice=" + discountPrice + ", discountPrecent="
				+ discountPrecent + ", quantity=" + quantity + ", numOfRating=" + numOfRating + ", numOfRewies="
				+ numOfRewies + ", title=" + title + ", description=" + description + ", brand=" + brand
				+ ", createdAt=" + createdAt + "]";
	}

	
	
	
}
