package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSectionRequest {

    @NotNull(message = "Category id required!")
    @Positive(message = "Must be greater then zero!")
    private Integer categoryId;

    @NotNull(message = "Section id is required!")
    @Positive(message = "Section id must be greater then zero!")
    private Integer sectionId;


    @NotBlank(message = "Section name is required")
    @Size(min = 3, max = 50, message = "Section name must be between 3 and 50 chars!")
    private String sectionName;
}
