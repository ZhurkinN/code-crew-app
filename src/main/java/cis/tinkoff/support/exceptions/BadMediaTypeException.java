package cis.tinkoff.support.exceptions;

public class BadMediaTypeException extends RuntimeException {
    public BadMediaTypeException() {
        super();
    }

    public BadMediaTypeException(String message) {
        super(message);
    }
}
