package cis.tinkoff.support.exceptions.constants;

public interface ErrorDisplayMessageKeeper {

    //CRUD action errors
    String RECORD_NOT_FOUND = "The record for this query was not found.";

    String PROJECT_WRONG_ACCESS = "Action with this project is not available.";

    //ProfilePictureNotFoundException
    String PICTURE_NOT_FOUND = "Profile picture for user with id = %d isn't found.";

    //UnavailableMediaTypeException
    String WRONG_MEDIA_TYPE = "User with email - %s tried to upload a file with an unavailable type. File name - %s";

    //UserAlreadyExistsException
    String USER_ALREADY_EXISTS = "User with email - %s already exists.";

    //RequestAlreadyExistsException
    String REQUEST_ALREADY_EXISTS = "User with email = %s tried to create request, that already exists. Current request is in consideration.";

    //InaccessibleActionException
    String INACCESSIBLE_RESUME_ACTION = "User with email = %s tried to perform inaccessible (He is not owner) action with a CV (resume with id = %d).";
    String INACCESSIBLE_PROJECT_ACTION = "User with email = %s tried to perform inaccessible (He is not leader) action with a project (project with id = %d).";
    String INACCESSIBLE_POSITION_ACTION = "User with email = %s tried to perform inaccessible (He is not leader) action with a position (position with id = %d).";
    String USER_ALREADY_IN_PROJECT = "User with email = %s is already in the project (position with id = %d). He can't join or be invited here.";

    //RecordNotFoundException
    String RESUME_NOT_FOUND = "CV with id = %d is not found or deleted.";
    String USER_NOT_FOUND = "User with id = %d is not found or deleted.";
    String USER_NOT_FOUND_BY_EMAIL = "User with email = %s is not found or deleted.";
    String POSITION_NOT_FOUND = "Position with id = %d is not found or deleted.";
    String POSITION_NOT_FOUND_BY_USER = "Position is not found by user with id = %d or deleted.";
    String PROJECT_NOT_FOUND = "Project with id = %d is not found or deleted.";
    String REQUEST_NOT_FOUND = "Position request with id = %d is not found or deleted.";
    String DIRECTION_NOT_FOUND = "Direction with id = %s is not found.";
    String PROJECT_STATUS_NOT_FOUND = "Project status with id = %s is not found.";
    String NOTIFICATION_TYPE_NOT_FOUND = "Notification type with id = %s is not found.";


}
