package cis.tinkoff.controller.model.custom;

import java.util.List;

public record CreateResumeDTO(
        String description,
        List<String> skills,
        String direction
) {
}
