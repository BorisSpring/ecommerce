package main.exception;

import java.io.Serial;

public class UserException extends RuntimeException {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 3478266568404820474L;

	public UserException(String message) {
		super(message);
	}
}
