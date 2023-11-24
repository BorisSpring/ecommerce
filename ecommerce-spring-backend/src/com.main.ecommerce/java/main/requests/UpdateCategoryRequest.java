package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @NotBlank(message = "Category name is required!")
    @Size(min = 3, max = 50 , message = "Cateogry name must be between 5 and 50 chars!")
    private String categoryName;

    @NotNull(message = "Category Id Is Required!")
    @Positive(message = "Category id must be greather then zero!")
    private Integer id;
}
