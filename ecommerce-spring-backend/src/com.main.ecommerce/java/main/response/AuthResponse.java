package main.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
public class AuthResponse {

	private boolean isAuth;
	private String jwt;
		
}
