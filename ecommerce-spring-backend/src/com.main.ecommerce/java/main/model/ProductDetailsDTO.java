package main.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ProductDetailsDTO {

	private ProductDTO product;
	private List<RatingDTO> ratingsDto;
	private List<ReviewDTO> reviewsDto;
	
}
