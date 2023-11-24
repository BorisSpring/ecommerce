package main.config;

import java.util.Arrays;
import java.util.Collections;
import main.filters.JwtGenerator;
import main.filters.JwtValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	 SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
		csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("_csrf");

		http.securityContext(securityContext -> securityContext.requireExplicitSave(false))
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf((csrf) -> csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		.cors((cors) -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowCredentials(true);
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setExposedHeaders(Arrays.asList("Authorization"));
			config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setMaxAge(8640000L);
			return config;
		}))
				.addFilterAfter(new JwtGenerator(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
		.authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
				.requestMatchers(HttpMethod.POST , "/api/v1/auth/**").permitAll()
				.requestMatchers(HttpMethod.PUT, "/api/v1/orders").authenticated()
				.requestMatchers(HttpMethod.GET , "/api/v1/sections/{categoryId}" , "/api/v1/orders/users/{orderId}" , "/api/v1/products","/api/v1/orders/{orderId}", "/api/v1/products/**" , "/api/v1/products/**/**" , "/api/v1/products/**/**/**" ,"/api/v1/categories").permitAll()
				.requestMatchers(HttpMethod.PATCH).hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/v1/categories", "/api/v1/sections", "/api/v1/sectionItems").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/v1/products", "/api/v1/categories", "/api/v1/sections", "/api/v1/sectionItems").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.DELETE,  "/api/v1/products","/api/v1/orders", "/api/v1/categories", "/api/v1/sections", "/api/v1/sectionItems").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.GET , "/api/v1/ratings", "api/v1/reviews", "/api/v1/orders").hasAuthority("ADMIN")
				.requestMatchers("/api/v1/carts", "/api/v1/ratings", "api/v1/reviews", "/api/v1/auth/changePassword").authenticated());


		
		
		return http.build();
		
	}
	

}


