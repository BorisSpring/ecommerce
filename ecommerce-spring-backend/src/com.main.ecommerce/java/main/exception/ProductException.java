package main.exception;


import java.io.Serial;

public class ProductException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 100_123_124_215_421L;

	public ProductException(String message) {
		super(message);
	}
}
