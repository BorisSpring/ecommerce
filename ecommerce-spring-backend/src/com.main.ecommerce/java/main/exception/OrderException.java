package main.exception;

import java.io.Serial;

public class OrderException extends RuntimeException{

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 4591563912324679171L;

	public OrderException(String message) {
		super(message);
	}
}
