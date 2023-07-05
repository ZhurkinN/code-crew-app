package cis.tinkoff.controller;

import cis.tinkoff.model.User;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Tag(name = "Users", description = "All actions with users.")
@Controller("/users")
public class UserController {

    @Inject
    private UserService userService;

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

    @Operation(method = "create", description = "Creates new user")
    @Post(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<User> create(User user) {
        return HttpResponse.ok(userService.save(user));
    }

    @Operation(method = "delete", description = "Deletes users by id")
    @Delete("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(method = "update", description = "Updates users by id")
    @Post(value = "/{id}", processes = MediaType.APPLICATION_JSON)
    public HttpResponse<User> update(@PathVariable Long id,
                                     User user) {
        return HttpResponse.ok(userService.update(id, user));
    }
}
