package cis.tinkoff.support.exceptions;

import static cis.tinkoff.support.exceptions.constants.LoggedErrorMessageKeeper.WRONG_MEDIA_TYPE;

public class UnavailableMediaTypeException extends RuntimeException {

    public UnavailableMediaTypeException() {
        super();
    }

    public UnavailableMediaTypeException(String message) {
        super(message);
    }

    public UnavailableMediaTypeException(String email,
                                         String fileName) {
        super(String.format(
                WRONG_MEDIA_TYPE,
                email,
                fileName
        ));
    }
}
