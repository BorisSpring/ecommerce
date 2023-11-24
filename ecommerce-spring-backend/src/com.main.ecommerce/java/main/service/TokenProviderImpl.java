package main.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import main.domain.User;
import main.filters.JwtConst;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenProviderImpl implements TokenProvider {


    @Override
    public String generateToken(User user) {

        SecretKey key= Keys.hmacShaKeyFor(JwtConst.SECRET.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .setIssuer("Boris Dimitrijevic")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+864000000))
                .claim("authorities", user.getAuthority().toString())
                .claim("username", user.getEmail())
                .signWith(key)
                .compact();

        return jwt;

    }

    @Override
    public String getEmailFromToken(String jwt) {

        SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECRET.getBytes(StandardCharsets.UTF_8));
        try {
            jwt = jwt.substring(7);

            Claims claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return String.valueOf(claim.get("username"));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid token received");
        }
    }
}
