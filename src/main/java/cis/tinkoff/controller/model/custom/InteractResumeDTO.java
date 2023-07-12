package cis.tinkoff.controller.model.custom;

import java.util.List;

public record InteractResumeDTO(
        String description,
        List<String> skills,
        String direction
) {
}
