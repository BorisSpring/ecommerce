package main.exception;

import java.io.Serial;

public class OrderItemException extends Exception {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -3814739228985080831L;

	
	public OrderItemException(String message) {
		super(message);
	}
}
