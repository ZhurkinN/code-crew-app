package cis.tinkoff.controller;

import cis.tinkoff.controller.model.UpdateUserDTO;
import cis.tinkoff.model.User;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Users", description = "All actions with users.")
@Controller("/api/v1/users")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(method = "findAll", description = "Finds all users")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Iterable<User>> findAll() {

        return HttpResponse.ok(userService.getAll());
    }

    @Operation(method = "findById", description = "Finds user by id")
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<User> findById(@PathVariable Long id) throws RecordNotFoundException {

        return HttpResponse.ok(userService.getById(id));
    }

    @Operation(method = "findByEmail", description = "Finds user by email")
    @Get(value = "/email", processes = MediaType.APPLICATION_JSON)
    public HttpResponse<User> findByEmail(@QueryValue String email) throws RecordNotFoundException {

        return HttpResponse.ok(userService.getByEmail(email));
    }

    @Operation(method = "update", description = "Updates information about user")
    @Patch(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<User> update(@Body UpdateUserDTO dto) throws RecordNotFoundException {

        User user = userService.update(dto.email(),
                dto.name(),
                dto.surname(),
                dto.contacts(),
                dto.pictureLink(),
                dto.mainInformation());
        return HttpResponse.ok(user);
    }

    @Operation(method = "delete", description = "Deletes users by id")
    @Delete("/{id}")
    public void delete(@PathVariable Long id) {

        userService.delete(id);
    }

    @Operation(method = "softDelete", description = "Sets 'deleted' flag true")
    @Post("/soft-delete/{id}")
    public void softDelete(@PathVariable Long id) {

        userService.softDelete(id);
    }
}
