package main.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userExceptionHandler(UserException ex, WebRequest request){
		
		ErrorDetails err = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), request.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorDetails> productExceptionHandler(ProductException ex, WebRequest request){
		
		ErrorDetails err = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), request.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorDetails> orderExceptionHadnler(OrderException ex, WebRequest request){
		
		ErrorDetails err = new ErrorDetails(ex.getMessage()	, LocalDateTime.now(), request.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(RatingException.class)
	public ResponseEntity<ErrorDetails> ratingExceptionHandler(RatingException ex, WebRequest req){
		ErrorDetails err = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), req.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(ReviewException.class)
	public ResponseEntity<ErrorDetails> reveiwExceptionHandler(ReviewException ex, WebRequest req){
		ErrorDetails err = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), req.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(CartException.class)
	public ResponseEntity<ErrorDetails> cartExceptionHandler(CartException ex, WebRequest req){
		ErrorDetails err = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), req.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	

	@ExceptionHandler(CartItemException.class)
	public ResponseEntity<ErrorDetails> cartItemExceptionHandler(CartItemException ex, WebRequest req){
		ErrorDetails err = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), req.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> badCredentialsExceptionHandler(BadCredentialsException ex, WebRequest req){
		ErrorDetails err = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), req.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
 