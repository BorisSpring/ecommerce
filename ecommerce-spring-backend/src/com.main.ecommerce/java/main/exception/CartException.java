package main.exception;

import java.io.Serial;

public class CartException extends Exception{

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 549106796425079588L;

	public CartException(String message) {
		super(message);
	}
}
