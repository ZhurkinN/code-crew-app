package cis.tinkoff.support.exceptions;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.PICTURE_NOT_FOUND;

public class ProfilePictureNotFoundException extends RuntimeException {

    public ProfilePictureNotFoundException() {
        super();
    }

    public ProfilePictureNotFoundException(String message) {
        super(message);
    }

    public ProfilePictureNotFoundException(Long id) {
        super(String.format(
                PICTURE_NOT_FOUND,
                id
        ));
    }
}
