package main.requests;

public class RatingRequest {

	private int productId;
	private int userId;
	private int ratingId;
	private double rating;
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
		
	public int getRatingId() {
		return ratingId;
	}

	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}
	
	public RatingRequest() {

	}

	public RatingRequest(int productId, int userId, double rating) {
		this.productId = productId;
		this.userId = userId;
		this.rating = rating;
	}
	
	
	
	
}
