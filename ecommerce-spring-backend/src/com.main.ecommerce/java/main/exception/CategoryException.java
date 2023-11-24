package main.exception;

public class CategoryException extends RuntimeException{

    private static  final long serialVersionUID = 6058947386948882186L;

    public CategoryException(String message) {
        super(message);
    }
}
