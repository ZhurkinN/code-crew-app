package cis.tinkoff.support.exceptions.constants;

public interface ErrorDisplayMessageKeeper {

    //CRUD action errors
    String RECORD_NOT_FOUND = "The record for this query was not found.";
    String USER_NOT_FOUND = "User was not found.";
    String RESUME_NOT_FOUND = "Resume was not found.";
    String POSITION_NOT_FOUND = "Position was not found.";
    String DIRECTION_NOT_FOUND = "Resume was not found.";
    String PROJECT_WRONG_ACCESS = "Action with this project is not available.";


    //Exception messages
    String PICTURE_NOT_FOUND = "Profile picture for user with id = %d wasn't found.";
    String WRONG_MEDIA_TYPE = "User with email - %s tried to upload a file with unavailable type. File name - %s";
    String USER_ALREADY_EXISTS = "User with email - %s already exists.";
    String REQUEST_ALREADY_EXISTS = "User with id = %d tried to create request, that already exists. Current request is in consideration.";
    String INACCESSIBLE_RESUME_ACTION = "User with id = %d tried to perform inaccessible (He is not owner) action with resume (resume with id = %d).";
    String INACCESSIBLE_PROJECT_ACTION = "User with id = %d tried to perform inaccessible (He is not leader) action with project (project with id = %d).";
    String INACCESSIBLE_POSITION_ACTION = "User with id = %d tried to perform inaccessible (He is not leader) action with position (position with id = %d).";
    String USER_ALREADY_IN_PROJECT = "User with id = %d is already in project (position with id = %d). He can't join or be invited here.";

}
