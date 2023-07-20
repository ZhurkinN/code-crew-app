package cis.tinkoff.support.exceptions;

public class InaccessibleActionException extends RuntimeException {

    public InaccessibleActionException() {
        super();
    }

    public InaccessibleActionException(String message) {
        super(message);
    }

    public InaccessibleActionException(String formattedMessage,
                                       String userEmail,
                                       Long inaccessibleRecordId) {
        super(String.format(
                formattedMessage,
                userEmail,
                inaccessibleRecordId
        ));
    }
}
