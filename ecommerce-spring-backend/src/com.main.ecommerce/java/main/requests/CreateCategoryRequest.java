package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {

    @NotBlank(message = "Category name is required!")
    @Size(min = 3, max = 50, message = "Category name must be between 5 and 50 chars")
    private String categoryName;
}
