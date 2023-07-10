package cis.tinkoff.controller;

import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.controller.model.custom.UpdateUserDTO;
import cis.tinkoff.model.User;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.mapper.UserMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Users", description = "All actions with users.")
@Controller("/api/v1/users")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(method = "findAll", description = "Finds all users")
    @Get(value = "/all", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<UserDTO>> findAll() {

        List<User> users = userService.getAll();
        List<UserDTO> responseDtos = userMapper.toDtos(users);
        return HttpResponse.ok(responseDtos);
    }

    @Operation(method = "findById", description = "Finds user by id")
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<UserDTO> findById(@PathVariable Long id)
            throws RecordNotFoundException, DeletedRecordFoundException {

        User user = userService.getById(id);
        UserDTO responseDto = userMapper.toDto(user);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "find", description = "Finds user")
    @Get(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<UserDTO> find(Authentication authentication)
            throws RecordNotFoundException, DeletedRecordFoundException {

        String email = authentication.getName();
        User user = userService.getByEmail(email);
        UserDTO responseDto = userMapper.toDto(user);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "update", description = "Updates information about user")
    @Patch(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<UserDTO> update(@Body UpdateUserDTO requestDto,
                                        Authentication authentication) throws RecordNotFoundException {

        User user = userService.update(authentication.getName(),
                requestDto.name(),
                requestDto.surname(),
                requestDto.contacts(),
                requestDto.pictureLink(),
                requestDto.mainInformation());
        UserDTO responseDto = userMapper.toDto(user);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "softDelete", description = "Sets 'deleted' flag true")
    @Delete
    public void softDelete(Authentication authentication) {

        String email = authentication.getName();
        userService.softDelete(email);
    }

    @Operation(method = "delete", description = "Deletes users by id")
    @Delete("/hard-delete")
    public void delete(@QueryValue Long id) {

        userService.delete(id);
    }
}
