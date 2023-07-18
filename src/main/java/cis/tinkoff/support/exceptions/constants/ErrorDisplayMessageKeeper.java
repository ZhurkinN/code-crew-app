package cis.tinkoff.support.exceptions.constants;

public interface ErrorDisplayMessageKeeper {

    //CRUD action errors
    String RECORD_NOT_FOUND = "The record for this query was not found.";
    String USER_NOT_FOUND = "User was not found.";
    String RESUME_NOT_FOUND = "Resume was not found.";
    String POSITION_NOT_FOUND = "Position was not found.";
    String DIRECTION_NOT_FOUND = "Resume was not found.";
    String USER_ALREADY_EXISTS = "A user with this email is already registered.";

    //Access errors
    String RESUME_WRONG_ACCESS = "Action with this resume is not available.";
    String USER_ALREADY_IN_PROJECT = "A user with this email is already in this project.";
    String SAME_REQUEST_ALREADY_EXISTS = "User already created same request. It's still in consideration.";
    String PROJECT_WRONG_ACCESS = "Action with this project is not available.";


    //Exception messages
    String PICTURE_NOT_FOUND = "Profile picture for user with id = %d wasn't found.";
    String WRONG_MEDIA_TYPE = "User with email - %s tried to upload a file with unavailable type. File name - %s";

}
