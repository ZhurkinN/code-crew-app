package cis.tinkoff.support.exceptions.handler;

import cis.tinkoff.support.exceptions.UserAlreadyExistsException;
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
@Requires(classes = {UserAlreadyExistsExceptionHandler.class, ExceptionHandler.class})
public class UserAlreadyExistsExceptionHandler implements ExceptionHandler<UserAlreadyExistsException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request,
                               UserAlreadyExistsException exception) {

        ErrorDTO dto = new ErrorDTO(
                request.getPath(),
                exception.getMessage(),
                HttpStatus.CONFLICT.getCode(),
                System.currentTimeMillis()
        );
        return HttpResponse
                .serverError(dto)
                .status(HttpStatus.CONFLICT);
    }
}
