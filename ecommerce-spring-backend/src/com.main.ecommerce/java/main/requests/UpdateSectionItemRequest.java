package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSectionItemRequest {

    @NotNull(message = "Section item id is required!")
    @Positive(message = "Section item id must be greather then zero!")
    private Integer sectionItemId;

    @NotNull(message = "Section  id is required!")
    @Positive(message = "Section iem id must be greather then zero!")
    private Integer sectionId;

    @NotBlank(message = "Section item name is required!")
    @Size(min = 3, max = 50, message = "Section item name must be between 3 and 50 chars!")
    private String sectionItemName;
}
