package cis.tinkoff.support.exceptions.constants;

public interface ErrorDisplayMessageKeeper {

    //CRUD action errors
    String RECORD_NOT_FOUND = "The record for this query was not found.";
    String USER_NOT_FOUND = "User was not found.";
    String RESUME_NOT_FOUND = "Resume was not found.";
    String REQUEST_NOT_FOUND = "Position request was not found.";
    String POSITION_NOT_FOUND = "Position was not found.";
    String DIRECTION_NOT_FOUND = "Resume was not found.";
    String USER_ALREADY_EXISTS = "A user with this email is already registered.";
    String DELETED_RECORD_FOUND = "Received data has been deleted or is unavailable.";

    //Access errors
    String RESUME_WRONG_ACCESS = "Action with this resume is not available.";
    String DELETED_OR_HIDDEN_RESUME_FOUND = "This resume has been deleted or hidden.";
    String USER_ALREADY_IN_PROJECT = "A user with this email is already in this project.";
    String SAME_REQUEST_ALREADY_EXISTS = "User already created same request. It's still in consideration.";
    String PROJECT_WRONG_ACCESS = "Action with this project is not available.";
}
