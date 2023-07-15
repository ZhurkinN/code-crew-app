package cis.tinkoff.support.exceptions;

import java.io.IOException;

public class BadAvatarPathException extends RuntimeException {
    public BadAvatarPathException() {
        super();
    }

    public BadAvatarPathException(String message) {
        super(message);
    }
}
