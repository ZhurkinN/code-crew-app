package cis.tinkoff.support.exceptions;

public class RequestEntityTooLargeException extends RuntimeException {
    public RequestEntityTooLargeException() {
        super();
    }

    public RequestEntityTooLargeException(String message) {
        super(message);
    }
}
