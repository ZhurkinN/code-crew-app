package cis.tinkoff.support.exceptions.constants;

public interface ErrorDisplayMessageKeeper {

    //CRUD action errors
    String RECORD_NOT_FOUND = "The record for this query was not found.";
    String USER_ALREADY_EXISTS = "A user with this email is already registered.";
    String DELETED_RECORD_FOUND = "Received data has been deleted or is unavailable.";

    //Access errors
    String RESUME_WRONG_ACCESS = "Action with this resume is not available.";
    String DELETED_OR_HIDDEN_RESUME_FOUND = "This resume has been deleted or hidden.";
}
