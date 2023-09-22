package main.requests;

public class UpdateCartItemRequest {

	private String size;
	private int cartItemId;
	private int quantity;
	private int userId;
	
	
	public UpdateCartItemRequest() {

	}
	
	public Integer getUserId() {
		return userId;
	}

		
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	

	


	
}
