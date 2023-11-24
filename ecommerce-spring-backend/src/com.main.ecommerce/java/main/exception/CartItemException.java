package main.exception;

import java.io.Serial;

public class CartItemException extends Exception {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -2883882122055170793L;

	public CartItemException(String message) {
		super(message);
	}
}
