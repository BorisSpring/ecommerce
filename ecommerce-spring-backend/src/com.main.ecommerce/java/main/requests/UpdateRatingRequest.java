package main.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRatingRequest {


    @NotNull(message = "Raiting id must not be null!")
    @Positive(message = "Rating id must be greather then zero!")
    private Integer ratingId;

    @NotNull(message = "Rating must not be null")
    @Positive(message = "Rating must be greather then zero!")
    private double rating;

}
