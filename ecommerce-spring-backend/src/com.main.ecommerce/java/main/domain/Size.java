package main.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Size {

	@Positive(message = "Size quantity must be greather then zero!")
	@Column(nullable = false)
	private int quantity = 0;

	@NotBlank(message = "Size must not be empty!")
	@Column(nullable = false, length = 3)
	private String size;

	
	
}
