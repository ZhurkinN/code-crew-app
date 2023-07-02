package cis.tinkoff.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;

import java.security.Principal;

@Controller("/auth")
@Secured({"ROLE_USER"})
public class AuthTestController {
    @Get("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<String> hello(final Principal principal) {
        return HttpResponse.status(HttpStatus.OK).body("hello");
    }

    @Get("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({"ROLE_ADMIN"})
    public HttpResponse<String> admin() {
        return HttpResponse.status(HttpStatus.OK).body("admin");
    }
}
