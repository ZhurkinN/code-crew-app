package cis.tinkoff.support.exceptions;

public class DeletedRecordFoundException extends RuntimeException {

    public DeletedRecordFoundException() {
        super();
    }

    public DeletedRecordFoundException(String message) {
        super(message);
    }
}
