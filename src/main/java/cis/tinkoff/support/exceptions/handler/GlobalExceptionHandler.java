package cis.tinkoff.support.exceptions.handler;

import cis.tinkoff.support.exceptions.*;
import cis.tinkoff.support.exceptions.constants.DisplayedErrorMessageKeeper;
import cis.tinkoff.support.exceptions.model.ErrorDTO;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Produces
@Singleton
@Requires(classes = {RuntimeException.class, ExceptionHandler.class})
public class GlobalExceptionHandler implements ExceptionHandler<RuntimeException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request,
                               RuntimeException exception) {

        log.error(exception.getMessage());
        ErrorDTO responseDto = new ErrorDTO()
                .setPath(request.getPath())
                .setLoggedMessage(exception.getMessage());
        setErrorCodeAndMessage(exception, responseDto);

        return HttpResponse
                .serverError(responseDto)
                .status(responseDto.getStatusCode());
    }

    private void setErrorCodeAndMessage(RuntimeException exception,
                                        ErrorDTO dto) {
        int statusCode;
        String displayedMessage;

        if (exception instanceof RecordNotFoundException) {

            statusCode = HttpStatus.NOT_FOUND.getCode();
            displayedMessage = DisplayedErrorMessageKeeper.RESOURCES_NOT_FOUND;
        } else if (exception instanceof InaccessibleActionException) {

            statusCode = HttpStatus.NOT_ACCEPTABLE.getCode();
            displayedMessage = DisplayedErrorMessageKeeper.INACCESSIBLE_ACTION;
        } else if (exception instanceof UserAlreadyExistsException) {

            statusCode = HttpStatus.CONFLICT.getCode();
            displayedMessage = DisplayedErrorMessageKeeper.USER_ALREADY_EXISTS;
        } else if (exception instanceof UnavailableMediaTypeException) {

            statusCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE.getCode();
            displayedMessage = DisplayedErrorMessageKeeper.UNAVAILABLE_MEDIA_TYPE;
        } else if (exception instanceof RequestAlreadyExistsException) {

            statusCode = HttpStatus.CONFLICT.getCode();
            displayedMessage = DisplayedErrorMessageKeeper.REQUEST_ALREADY_EXISTS;
        } else if (exception instanceof RequestAlreadyProcessedException) {

            statusCode = HttpStatus.CONFLICT.getCode();
            displayedMessage = DisplayedErrorMessageKeeper.REQUEST_ALREADY_PROCESSED;
        } else if (exception instanceof ProfilePictureNotFoundException) {

            statusCode = HttpStatus.NOT_FOUND.getCode();
            displayedMessage = DisplayedErrorMessageKeeper.PROFILE_PICTURE_NOT_FOUND;
        } else {

            statusCode = HttpStatus.BAD_REQUEST.getCode();
            displayedMessage = exception.getMessage();
        }

        dto.setMessage(displayedMessage).setStatusCode(statusCode);
    }
}
