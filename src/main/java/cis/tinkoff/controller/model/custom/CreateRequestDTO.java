package cis.tinkoff.controller.model.custom;

import io.micronaut.core.annotation.Nullable;

public record CreateRequestDTO(
        Long resumeId,
        Long positionId,
        @Nullable
        String coverLetter
) {
}
