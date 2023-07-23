package cis.tinkoff.controller;

import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.controller.model.custom.RegisterUserDTO;
import cis.tinkoff.controller.model.custom.UpdateUserDTO;
import cis.tinkoff.model.User;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.mapper.UserMapper;
import io.micrometer.core.annotation.Timed;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
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
    private final UserMapper userMapper;

    @Timed(
            value = "cis.tinkoff.controller.userController.findById",
            percentiles = {0.5, 0.95, 0.99},
            description = "Finds user by id"
    )
    @Operation(method = "findById", description = "Finds user by id")
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<UserDTO> findById(@PathVariable Long id) {

        User user = userService.getById(id);
        UserDTO responseDto = userMapper.toDto(user);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "find", description = "Finds user")
    @Get(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<UserDTO> find(Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getByEmail(email);
        UserDTO responseDto = userMapper.toDto(user);
        return HttpResponse.ok(responseDto);
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Operation(method = "register", description = "Registers new user")
    @Post(uri = "/register", processes = MediaType.APPLICATION_JSON)
    public HttpResponse<UserDTO> register(@Body RegisterUserDTO dto) {

        User user = userService.register(
                dto.email(),
                dto.password(),
                dto.name(),
                dto.surname()
        );
        UserDTO responseDto = userMapper.toDto(user);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "update", description = "Updates information about user")
    @Patch(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<UserDTO> update(@Body UpdateUserDTO requestDto,
                                        Authentication authentication) {

        User user = userService.update(
                authentication.getName(),
                requestDto.name(),
                requestDto.surname(),
                requestDto.contacts(),
                requestDto.pictureLink(),
                requestDto.mainInformation()
        );
        UserDTO responseDto = userMapper.toDto(user);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "softDelete", description = "Sets 'deleted' flag true")
    @Delete
    public void softDelete(Authentication authentication) {

        String email = authentication.getName();
        userService.softDelete(email);
    }

}
