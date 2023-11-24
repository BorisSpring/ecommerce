package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorDetailsDTO {

	private String message;
	private LocalDateTime timestamp;
	private String description;

	
}
