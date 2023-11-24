package main.service;

import main.exception.CartException;
import main.requests.CreateUserRequest;
import main.requests.LoginRequest;
import main.requests.UpdatePasswordRequest;
import main.response.AuthResponse;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthService {

    AuthResponse loginUser(LoginRequest loginRequest) throws BadCredentialsException;

    AuthResponse registerUser(CreateUserRequest createUserRequest) throws CartException;

    void updatePassword(UpdatePasswordRequest updatePasswordRequest, String jwt);

}
