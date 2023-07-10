package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.DirectionDictionary;

public record RequestsChoiceResumeDTO(
        Long id,
        DirectionDictionary direction
) {
}
