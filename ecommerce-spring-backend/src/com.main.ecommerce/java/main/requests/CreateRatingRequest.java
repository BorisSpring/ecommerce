package main.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateRatingRequest {

	@NotNull(message = "Product is is required!")
	private Integer productId;

	@NotNull(message = "Raiting is required!")
	private double rating;

}
