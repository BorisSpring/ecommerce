package main.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.exception.CartException;
import main.requests.CreateUserRequest;
import main.requests.LoginRequest;
import main.requests.UpdatePasswordRequest;
import main.response.AuthResponse;
import main.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

	private  final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createNewUser(@Valid @RequestBody CreateUserRequest createUserRequest) throws CartException {
		return  ResponseEntity.ok(authService.registerUser(createUserRequest));
	}

	@PostMapping("/login")
	public  ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws BadCredentialsException {
		return ResponseEntity.ok(authService.loginUser(loginRequest));
	}

	@PostMapping("/changePassword")
	@ResponseStatus(HttpStatus.OK)
	public  void changePasswordHandler(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
									   @RequestHeader("Authorization") String jwt){
		authService.updatePassword(updatePasswordRequest, jwt);
	}
		
}
