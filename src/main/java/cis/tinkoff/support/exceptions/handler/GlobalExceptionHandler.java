package cis.tinkoff.support.exceptions.handler;

import cis.tinkoff.support.exceptions.*;
import cis.tinkoff.support.exceptions.model.ErrorDTO;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {RuntimeException.class, ExceptionHandler.class})
public class GlobalExceptionHandler implements ExceptionHandler<RuntimeException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request,
                               RuntimeException exception) {

        int statusCode = getStatusCode(exception);
        ErrorDTO dto = new ErrorDTO(
                request.getPath(),
                exception.getMessage(),
                statusCode,
                System.currentTimeMillis()
        );
        return HttpResponse
                .serverError(dto)
                .status(statusCode);
    }

    private int getStatusCode(RuntimeException exception) {

        int statusCode;
        if (exception instanceof DeletedRecordFoundException
                || exception instanceof RecordNotFoundException
                || exception instanceof ProfilePictureNotFoundException) {
            statusCode = HttpStatus.NOT_FOUND.getCode();
        } else if (exception instanceof InaccessibleActionException) {
            statusCode = HttpStatus.NOT_ACCEPTABLE.getCode();
        } else if (exception instanceof UnavailableMediaTypeException) {
            statusCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE.getCode();
        } else {
            statusCode = HttpStatus.CONFLICT.getCode();
        }

        return statusCode;
    }
}
