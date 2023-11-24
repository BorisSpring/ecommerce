package main.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @Email(message = "Email is required!")
    private String email;

    @NotBlank(message = "First name is required!")
    @Size(min = 5, max = 50, message = "First name must be between 5 and 50 chars!")
    private  String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(min = 5, max = 50, message = "Last name must be between 5 and 50 chars!")
    private String lastName;

    @PastOrPresent(message = "Date of brith must be in present")
    private LocalDate birth;

    @NotBlank(message = "Password is required!")
    @Size(min = 5, max = 15 , message = "Password must be between 5 and 15 chars!")
    private String password;

    @NotBlank(message = "Repaeted password is required!")
    @Size(min = 5, max = 15 , message = "Password must be between 5 and 15 chars!")
    private String repeatedPassword;

    @AssertTrue(message = "Password must match!")
    private boolean isPassword(){
        return  password != null && password.equals(repeatedPassword);
    }


}
