package main.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
import main.model.ErrorDetailsDTO;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {



	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetailsDTO> userExceptionHandler(UserException ex, WebRequest request){
		
		return ResponseEntity.badRequest()
								.body(ErrorDetailsDTO.builder()
								.message(ex.getMessage())
								.timestamp(LocalDateTime.now())
								.description(request.getDescription(false))
								.build());
	}
	
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorDetailsDTO> productExceptionHandler(ProductException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}
	
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorDetailsDTO> orderExceptionHadnler(OrderException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}
	
	@ExceptionHandler(RatingException.class)
	public ResponseEntity<ErrorDetailsDTO> ratingExceptionHandler(RatingException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}
	
	@ExceptionHandler(ReviewException.class)
	public ResponseEntity<ErrorDetailsDTO> reveiwExceptionHandler(ReviewException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}
	
	@ExceptionHandler(CartException.class)
	public ResponseEntity<ErrorDetailsDTO> cartExceptionHandler(CartException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}
	

	@ExceptionHandler(CartItemException.class)
	public ResponseEntity<ErrorDetailsDTO> cartItemExceptionHandler(CartItemException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}

	@ExceptionHandler(SectionException.class)
	public ResponseEntity<ErrorDetailsDTO> sectionExceptionHandler(SectionException ex, WebRequest request){

		return ResponseEntity.badRequest()
				.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}

	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<ErrorDetailsDTO> categoryExceptionHandler(CategoryException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}
	

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetailsDTO> badCredentialsExceptionHandler(BadCredentialsException ex, WebRequest request){

		return ResponseEntity.badRequest()
						.body(ErrorDetailsDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.description(request.getDescription(false))
						.build());
	}


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetailsDTO> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request){

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
										.body(ErrorDetailsDTO.builder()
										.message(ex.getMessage())
										.timestamp(LocalDateTime.now())
										.description(request.getDescription(false))
										.build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidException(MethodArgumentNotValidException ex){

					Map<String,String> errors = new HashMap<>();

					ex.getBindingResult().getAllErrors().forEach(objectError -> {
						String fieldName = ((FieldError) objectError).getField();
						errors.put(fieldName, objectError.getDefaultMessage());
					});

					return  ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public  ResponseEntity<Map<String,String>> constraintViolationException(ConstraintViolationException ex){

					Map<String,String> errors = new HashMap<>();

					ex.getConstraintViolations().forEach(constraintViolation -> {
						String fieldName = constraintViolation.getPropertyPath().toString();
						errors.put(fieldName, constraintViolation.getMessage());
					});

					return  ResponseEntity.badRequest().body(errors);
	}


	@ExceptionHandler(DataIntegrityViolationException.class)
	public  ResponseEntity<String> dataIntegrityViolationException(DataIntegrityViolationException ex){

		return  ResponseEntity.badRequest().body(ex.getCause().getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsDTO> universalExceptionHandler(Exception ex, WebRequest request){

		return  ResponseEntity.internalServerError()
						.body(ErrorDetailsDTO.builder()
						.description(request.getDescription(false))
						.timestamp(LocalDateTime.now())
						.message(ex.getCause().getMessage())
						.build());
	}

}
 