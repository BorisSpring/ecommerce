package main.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {

    @NotBlank(message = "Old password is required!")
    @Size(min = 5, max = 15 , message = "Old password must have between 5 and 15 chars!")
    private String oldPassword;


    @NotBlank(message = "New password is required!")
    @Size(min = 5, max = 15 , message = "New password must have between 5 and 15 chars!")
    private String newPassword;

    @NotBlank(message = "Repeated password is required!")
    @Size(min = 5, max = 15 , message = "Repeated password must have between 5 and 15 chars!")
    private String repeatedPassword;

    @AssertTrue
    private boolean isNewPassword(){
        return  this.newPassword != null && newPassword.equals(repeatedPassword);
    }
}
