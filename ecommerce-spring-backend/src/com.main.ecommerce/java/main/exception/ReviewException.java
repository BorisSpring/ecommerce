package main.exception;

import java.io.Serial;

public class ReviewException extends Exception {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 106351582665165744L;
	
	
	public ReviewException(String message) {
		super(message);
	}
}
