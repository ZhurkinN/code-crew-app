package cis.tinkoff.controller.model.custom;

public record RegisterUserDTO(
        String email,
        String password,
        String name,
        String surname
) {
}
