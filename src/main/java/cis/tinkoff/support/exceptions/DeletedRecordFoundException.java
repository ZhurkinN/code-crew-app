package cis.tinkoff.support.exceptions;

public class DeletedRecordFoundException extends Exception {

    public DeletedRecordFoundException() {
        super();
    }

    public DeletedRecordFoundException(String message) {
        super(message);
    }
}
