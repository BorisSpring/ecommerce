package main.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.config.TokenProvider;
import main.entity.Authority;
import main.entity.User;
import main.exception.CartException;
import main.exception.UserException;
import main.repository.AuthorityRepository;
import main.repository.UserRepository;
import main.requests.LoginRequest;
import main.response.AuthResponse;
import main.service.CartService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepostiory;
	private TokenProvider tokenProvider;
	private CartService cartService;
	private AuthorityRepository authorityRepo;
	
	
	



	public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepostiory, TokenProvider tokenProvider,
			CartService cartService, AuthorityRepository authorityRepo) {
		this.passwordEncoder = passwordEncoder;
		this.userRepostiory = userRepostiory;
		this.tokenProvider = tokenProvider;
		this.cartService = cartService;
		this.authorityRepo = authorityRepo;
	}




	

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req){
		Authentication auth = authenthicate(req);
		String token = tokenProvider.generateToken(auth);
		return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(true, token));
					
	}


	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUpHandler(@RequestBody User user) throws CartException{
		String email = user.getEmail();
		String password = user.getPassword();
		boolean exist = userRepostiory.existsByEmail(email);
		
		
		if(exist) {
			throw new UserException("Email is alerdy in use!");
		}
		
		
		Authority authority = authorityRepo.findByAuthority("ADMIN");
		
		if(authority == null) {
			authority = new Authority();
			authority.setAuthority("ADMIN");
			authority = authorityRepo.save(authority);
		}
		
		user.setAuthority(authority);
		user.setPassword(passwordEncoder.encode(password));
		User savedUser = userRepostiory.save(user);
		
		if(savedUser == null) {
			throw new UserException("Failed to register please try again!");
		}
		
		cartService.createCart(savedUser.getId());
		
		Authentication auth =  new UsernamePasswordAuthenticationToken(email, password, Arrays.asList(new SimpleGrantedAuthority("user")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = tokenProvider.generateToken(auth);
		return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(true, jwt));
	}

	
	public Authentication authenthicate(LoginRequest req) {
		
		User user = userRepostiory.findUserByEmail(req.getEmail());
		if(user == null  || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			throw new BadCredentialsException("Invalid Credentials");
		}
		
		
		List<GrantedAuthority> authorities  = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getAuthority().getAuthority()));
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
		
	}
	
		
}
