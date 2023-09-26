package main.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean
	 SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		

		http.securityContext(context -> context.requireExplicitSave(false));
		
		http.csrf(csrf -> csrf.disable()).
		sessionManagement((session) ->
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(HttpMethod.POST, "/api/products/findCategories", "/api/products/similar/{itemName}/{numberOfElements}" , "/api/products/section/itemName/{itemNameId}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/products/category/{category}", "/api/products/search/{query}" , "/api/products/{id}", "/api/products/all").permitAll()
				.requestMatchers("/auth/login", "/auth/signup").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/orders", "/api/ratings/findAll" , "/api/reviews").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/orders", "/api/products/{id}", "/api/ratings" , "/api/reviews").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/orders/{orderId}/cancel", "/api/orders/delivered", "/api/orders/ship" , "/api/products").hasAuthority("ADMIN")
				.requestMatchers("/api/**").authenticated()
				)
		.cors((cors) -> cors.configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setExposedHeaders(Arrays.asList("Authorization"));
				config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setMaxAge(8640000L);
				return config;
			}
		}))
		.addFilterAfter(new JwtGenerator(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
		
		
		return http.build();
		
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

//CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//requestHandler.setCsrfRequestAttributeName("_csrf");
//http.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/auth/signup","/auth/login")
//		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
