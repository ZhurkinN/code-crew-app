package cis.tinkoff.controller;

import cis.tinkoff.controller.model.RegisterUserDTO;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.UserAlreadyExistsException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Tag(name = "Authorization", description = "All actions with users authorization/registration.")
@Controller
public class AuthController {

    @Inject
    private UserService userService;

    @Operation(method = "register", description = "Registers new user")
    @Post(uri = "/register", processes = MediaType.APPLICATION_JSON)
    public HttpResponse<RegisterUserDTO> register(@Body RegisterUserDTO dto)
            throws UserAlreadyExistsException {

        userService.register(
                dto.email(),
                dto.password(),
                dto.name(),
                dto.surname()
        );
        return HttpResponse.ok(dto);

    }

}