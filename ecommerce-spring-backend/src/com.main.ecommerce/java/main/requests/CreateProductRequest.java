package main.requests;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.domain.Size;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequest {

	@NotNull(message = "Price is required")
	@Min(value = 1, message = "Price must be at least 1 euro!")
	private double price;
	private double discountPrice;
	private Integer discountPrecent;

	@NotBlank(message = "Title required!")
	@jakarta.validation.constraints.Size(message = "Title must be between 5 and 20 chars!")
	private String title;

	@NotBlank(message = "Description required!")
	@jakarta.validation.constraints.Size(min = 20, max = 500, message = "Description must have between 20 and 500 chars!")
	private String description;

	@NotNull(message = "Section Item Category is needed!")
	@Positive(message = "Section item id must be greather then zero!")
	private Integer sectionItemId;

//	@NotBlank(message = "Top level category required!")
//	@jakarta.validation.constraints.Size(min = 5, max = 50, message = "Nmae must be between 5 and 50 chars")
//	private String topLevelCategory;

//	@NotBlank(message = "Second level category required!")
//	@jakarta.validation.constraints.Size(min = 5, max = 50, message = "Nmae must be between 5 and 50 chars")
//	private String secondLevelCategory;
//
//	@NotBlank(message = "Third level category required!")
//	@jakarta.validation.constraints.Size(min = 5, max = 50, message = "Nmae must be between 5 and 50 chars")
//	private String thirdLevelCategory;

	@NotBlank(message = "Brand Required!")
	@jakarta.validation.constraints.Size(min = 5, max = 50, message = "Brand must be between 5 and 50 chars!")
	private String brand;

	@NotBlank(message = "Name is required")
	@jakarta.validation.constraints.Size(min = 5, max = 50, message = "Nmae must be between 5 and 50 chars")
	private String name;


	@NotNull(message = "Colors must not be null!")
	@NotEmpty(message = "Product must have colors!")
	private List<String> colors = new ArrayList<>();


	@NotNull(message = "Images must not be null!")
	@NotEmpty(message = "Products must have images!")
	private List<MultipartFile> imageFiles = new ArrayList<>();

	@NotNull(message = "Highlights must not be null!")
	@NotEmpty(message = "Product  must have at least one highlight!")
	private List<String> highlights = new ArrayList<>();

	@NotNull(message = "Size must not be null!")
	@NotEmpty(message = "Product must have at least one size!")
	private Set<Size> sizes= new HashSet<>();

}
