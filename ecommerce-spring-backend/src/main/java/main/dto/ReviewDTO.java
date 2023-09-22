package main.dto;

import java.time.LocalDateTime;

public class ReviewDTO {

	private int id;
	private String userEmail;
	private String reviewContent;
	private LocalDateTime createdAt;
	private String productTitle;;
	
	
	public ReviewDTO(int id, String userEmail, String reviewContent, LocalDateTime createdAt, String productTitle) {
		this.id = id;
		this.userEmail = userEmail;
		this.reviewContent = reviewContent;
		this.createdAt = createdAt;
		this.productTitle = productTitle;
	}
	

	
	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	
	
}
