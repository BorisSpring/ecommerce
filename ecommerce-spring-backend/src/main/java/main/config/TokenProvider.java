package main.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {

	public String generateToken(Authentication auth) {
		
		SecretKey key= Keys.hmacShaKeyFor(JwtConst.SECRET.getBytes(StandardCharsets.UTF_8));
		String jwt = Jwts.builder()
				.setIssuer("Boris Dimitrijevic")
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+864000000))
				.claim("authorities", auth.getAuthorities().toString())
				.claim("username", auth.getName())
				.signWith(key)
				.compact();
			
		return jwt;
		
	}
	
	public String getEmailFromToken(String jwt) {
		
		
		SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECRET.getBytes(StandardCharsets.UTF_8));
		try {
			jwt = jwt.substring(7);

			Claims claim = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwt)
					.getBody();
			
			String email = String.valueOf(claim.get("username"));
			return email;
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid token received");
		}
	}

	
}
