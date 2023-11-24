package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateReviewRequest {

    @NotNull(message = "Review id is required!")
    @Positive(message = "Review id must be greather then zero!")
    private Integer reviewId;

    @NotBlank(message = "Review must not be empty!")
    @Size(min = 5, max = 100, message = "Review must be between 5 and 100 chars!")
    private String review;
}
