package cis.tinkoff.support.exceptions;

public class InaccessibleActionException extends RuntimeException {

    public InaccessibleActionException() {
        super();
    }

    public InaccessibleActionException(String message) {
        super(message);
    }
}
