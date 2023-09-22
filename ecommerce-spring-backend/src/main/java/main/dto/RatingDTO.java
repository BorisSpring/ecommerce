package main.dto;


public class RatingDTO {

	private int id;
	private double rating;
	private String userEmail;
	private String productTitle;
	

	
	
	
	public RatingDTO(int id) {
		super();
		this.id = id;
	}

	public RatingDTO(int id, double rating, String userEmail, String productTitle) {
		this.id = id;
		this.rating = rating;
		this.userEmail = userEmail;
		this.productTitle = productTitle;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	
	
	
	
}
