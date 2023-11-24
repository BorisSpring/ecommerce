package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionRequest {

    @NotNull(message = "Category Id Required!")
    @Positive(message = "Must be greather then zero!")
    private Integer categoryId;

    @NotBlank(message = "Section name is required!")
    @Size(min = 3, max = 50 , message = "Section name must be between 3 and 50 chars!")
    private String sectionName;
}
