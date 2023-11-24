package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemRequest {

    @NotNull(message = "Product id is required!")
    @Positive(message = "Product id must be greather then zero!")
    private Integer productId;

    @NotNull(message = "Quantity is required!")
    @Positive(message = "Quantity must be greather then zero!")
    private Integer quantity;

    @NotBlank(message = "Size must not be empty!")
    private String size;

    @NotBlank(message = "Color must not be empty!")
    private String color;
}
