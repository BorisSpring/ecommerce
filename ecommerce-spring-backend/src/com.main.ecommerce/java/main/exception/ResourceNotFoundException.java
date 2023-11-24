package main.exception;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6643837031360832044L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
