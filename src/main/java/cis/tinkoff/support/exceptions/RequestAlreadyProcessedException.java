package cis.tinkoff.support.exceptions;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.REQUEST_ALREADY_PROCESSED;

public class RequestAlreadyProcessedException extends RuntimeException {

    public RequestAlreadyProcessedException() {
        super();
    }

    public RequestAlreadyProcessedException(String message) {
        super(message);
    }

    public RequestAlreadyProcessedException(String userEmail,
                                            Long requestId) {
        super(String.format(
                REQUEST_ALREADY_PROCESSED,
                userEmail,
                requestId
        ));
    }
}
