package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateReviewRequest {

	@NotNull(message = "Product id is required!")
	@Positive(message = "Product id must be positive")
	private Integer productId;

	@NotBlank(message = "Content is required")
	@Size(min = 5, max = 100, message = "Content must be between 5 and 100 chars!")
	private String content;
	
	

	
	
}
