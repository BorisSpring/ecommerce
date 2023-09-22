package main.requests;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.entity.Size;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequest {

	private int id;
	private int price;
	private int discountPrice;
	private int discountPrecent;
	private int quantity;
	
	private String title;
	private String description;
	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;
	private String brand;
	private String name;
	
	private List<String> color = new ArrayList<>();
	private List<String> imageUrl = new ArrayList<>();
	private List<String> highlights = new ArrayList<>();
	private Set<Size> sizes= new HashSet<>();

	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreateProductRequest() {

	}
	
	public List<String> getHighlights() {
		return highlights;
	}




	public void setHighlights(List<String> highlights) {
		this.highlights = highlights;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getDiscountPrice() {
		return discountPrice;
	}


	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getDiscountPrecent() {
		return discountPrecent;
	}


	public void setDiscountPrecent(int discountPrecent) {
		this.discountPrecent = discountPrecent;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public List<String> getColor() {
		return color;
	}


	public void setColor(List<String> color) {
		this.color = color;
	}


	public Set<Size> getSizes() {
		return sizes;
	}


	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}


	public List<String> getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getTopLevelCategory() {
		return topLevelCategory;
	}


	public void setTopLevelCategory(String topLevelCategory) {
		this.topLevelCategory = topLevelCategory;
	}


	public String getSecondLevelCategory() {
		return secondLevelCategory;
	}


	public void setSecondLevelCategory(String secondLevelCategory) {
		this.secondLevelCategory = secondLevelCategory;
	}


	public String getThirdLevelCategory() {
		return thirdLevelCategory;
	}


	public void setThirdLevelCategory(String thirdLevelCategory) {
		this.thirdLevelCategory = thirdLevelCategory;
	}


	@Override
	public String toString() {
		return "CreateProductRequest [price=" + price + ", discountPrice=" + discountPrice + ", title=" + title
				+ ", description=" + description + ", discountPrecent=" + discountPrecent + ", quantity=" + quantity
				+ ", brand=" + brand + ", color=" + color + ", sizes=" + sizes + ", imageUrl=" + imageUrl
				+ ", topLevelCategory=" + topLevelCategory + ", secondLevelCategory=" + secondLevelCategory
				+ ", thirdLevelCategory=" + thirdLevelCategory + "]";
	}

	

}
