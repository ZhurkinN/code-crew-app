package cis.tinkoff.controller.model;

public record RegisterUserDTO(
        String email,
        String password,
        String name,
        String surname
) {
}
