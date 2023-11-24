package main.exception;


import java.io.Serial;

public class RatingException extends Exception{

	@Serial
	private static final long serialVersionUID = 4891025793919072750L;
	public RatingException(String message) {
		super(message);
	}
}
