package cis.tinkoff.support.exceptions;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.REQUEST_ALREADY_EXISTS;

public class RequestAlreadyExistsException extends RuntimeException {

    public RequestAlreadyExistsException() {
        super();
    }

    public RequestAlreadyExistsException(String userEmail) {
        super(String.format(
                REQUEST_ALREADY_EXISTS,
                userEmail
        ));
    }
}
