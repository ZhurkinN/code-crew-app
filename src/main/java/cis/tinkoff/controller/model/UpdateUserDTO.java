package cis.tinkoff.controller.model;

import java.util.List;

public record UpdateUserDTO(
        String name,
        String surname,
        List<String> contacts,
        String pictureLink,
        String mainInformation
) {
}
