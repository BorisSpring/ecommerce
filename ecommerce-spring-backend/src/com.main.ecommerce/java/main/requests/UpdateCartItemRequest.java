package main.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateCartItemRequest {

	private String size;
	private int cartItemId;
	private int quantity;
	private int userId;
	


	
}
