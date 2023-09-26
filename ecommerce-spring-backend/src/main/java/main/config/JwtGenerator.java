package main.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtGenerator extends OncePerRequestFilter {


	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("generator");
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( auth != null) {
			try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECRET.getBytes(StandardCharsets.UTF_8));
				
				String jwt = Jwts.builder()
						.setIssuer("Boris Dimtirijevic")
						.setIssuedAt(new Date())
						.setExpiration(new Date(new Date().getTime()+ 8640000))
						.claim("username", auth.getName())
						.claim("authorities" , populateAuthorities(auth.getAuthorities()))
						.signWith(key)
						.compact();


				response.setHeader("Authorization", jwt);						
				
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid token received");
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities )  {
		
		List<String> userAuthorities = new ArrayList<>();
		
		for(GrantedAuthority authority : authorities) {
			userAuthorities.add(authority.getAuthority());
		}
		
		return String.join(",", userAuthorities);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		return !request.getServletPath().equals("/auth/login");
	}

}
