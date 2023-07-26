package cis.tinkoff.support.exceptions;

import static cis.tinkoff.support.exceptions.constants.LoggedErrorMessageKeeper.USER_ALREADY_EXISTS;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String email) {
        super(String.format(
                USER_ALREADY_EXISTS,
                email
        ));
    }
}
