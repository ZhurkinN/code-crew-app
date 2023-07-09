package cis.tinkoff.controller.model;

import java.util.List;

public record CreateResumeDTO(
        String description,
        List<String> skills,
        String direction
) {
}
