package cis.tinkoff.controller.model.custom;

import java.util.List;

public record UpdateUserDTO(
        String name,
        String surname,
        List<String> contacts,
        String pictureLink,
        String mainInformation
) {
}
