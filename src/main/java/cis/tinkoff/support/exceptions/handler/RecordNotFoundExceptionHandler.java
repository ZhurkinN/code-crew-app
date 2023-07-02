package cis.tinkoff.support.exceptions.handler;

import cis.tinkoff.support.RecordNotFoundException;
import cis.tinkoff.support.exceptions.model.ErrorDTO;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;

@Produces
@Singleton
@Requires(classes = {RecordNotFoundException.class, ExceptionHandler.class})
public class RecordNotFoundExceptionHandler implements ExceptionHandler<RecordNotFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, RecordNotFoundException exception) {

        ErrorDTO dto = new ErrorDTO(
                request.getPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getCode(),
                LocalDateTime.now()
        );
        return HttpResponse.serverError(dto).status(HttpStatus.BAD_REQUEST);
    }
}
