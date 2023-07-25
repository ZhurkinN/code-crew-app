package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.dictionary.DirectionDictionary;

public record RequestsChoiceResumeDTO(
        Long id,
        DirectionDictionary direction
) {
}
