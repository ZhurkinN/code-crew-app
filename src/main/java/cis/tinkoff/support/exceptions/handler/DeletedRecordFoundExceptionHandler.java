package cis.tinkoff.support.exceptions.handler;

import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
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
@Requires(classes = {DeletedRecordFoundException.class, ExceptionHandler.class})
public class DeletedRecordFoundExceptionHandler implements ExceptionHandler<DeletedRecordFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request,
                               DeletedRecordFoundException exception) {

        ErrorDTO dto = new ErrorDTO(
                request.getPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getCode(),
                System.currentTimeMillis()
        );
        return HttpResponse
                .serverError(dto)
                .status(HttpStatus.BAD_REQUEST);
    }
}