package main.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader("Authorization");
		if(jwt != null) {
			try {
				jwt = jwt.substring(7);
				SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECRET.getBytes());

				Claims claim = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();
				String username = String.valueOf(claim.get("username"));
				String authorities = String.valueOf(claim.get("authorities"));
				authorities = authorities.replace("[", "");
				authorities = authorities.replace("]", "");
				
				ArrayList<GrantedAuthority> authority = new ArrayList<>();
				authority.add(new SimpleGrantedAuthority(authorities));
				
				Authentication  auth = new UsernamePasswordAuthenticationToken(username,null, authority);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid Token Received! JwtValidator");
			}
		}
	
		filterChain.doFilter(request, response);
	}
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		  String requestURI = request.getRequestURI();
		    String requestMethod = request.getMethod();

		    return (requestMethod.equals("POST") && (
		            requestURI.startsWith("/api/products/findCategories") ||
		            requestURI.matches("/api/products/similar/[^/]+/\\d+")
		        )) ||
		        (requestMethod.equals("GET") && (
		            requestURI.startsWith("/api/products/category") ||
		            requestURI.startsWith("/api/products/search") ||
		            requestURI.startsWith("/api/products/all") ||
		            requestURI.matches("/api/products/\\d+")
		        )) ||
		        requestURI.equals("/auth/login") ||
		        requestURI.equals("/auth/signup");
	}
	
}
