package cis.tinkoff.support.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String formattedMessage,
                                   Long notFoundRecordId) {
        super(String.format(
                formattedMessage,
                notFoundRecordId
        ));
    }

    public RecordNotFoundException(String formattedMessage,
                                   String notFoundRecordParameter) {
        super(String.format(
                formattedMessage,
                notFoundRecordParameter
        ));
    }
}
