package main.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

	@NotBlank(message = "Email is required!")
	@Email(message = "Invalid input for email!")
	private String email;

	@NotBlank(message = "Passsword is required!")
	@Size(min = 5, max = 15, message = "Password must be between 5 and 15 chars!")
	private String password;

	
}
