package main.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequest {

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits!")
	private String mobileNumber;

	@NotBlank(message = "Address is required!")
	private String adress;

	@NotBlank(message = "City is required!")
	private String city;

	@NotBlank(message = "Country is required!")
	private String country;

	@NotBlank(message = "Postal code is required!")

	private String postalCode;

	@NotBlank(message = "State is required!")
	private String state;

	@NotBlank(message = "First name is required!")
	private String firstName;

	@NotBlank(message = "Last name is required!")
	private String lastName;


}
