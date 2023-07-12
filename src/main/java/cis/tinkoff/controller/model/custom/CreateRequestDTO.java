package cis.tinkoff.controller.model.custom;

public record CreateRequestDTO(
        Long resumeId,
        Long positionId,
        String coverLetter
) {
}
