package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.request.PositionRequestDTO;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Singleton
@RequiredArgsConstructor
public class PositionRequestMapper extends GenericMapper<PositionRequest, PositionRequestDTO> {
    private final VacancyMapper vacancyMapper;
    @Override
    public PositionRequestDTO toDto(PositionRequest model) {
        if (Objects.isNull(model)) {
            return null;
        }

        PositionRequestDTO dto = new PositionRequestDTO()
                .setVacancy(vacancyMapper.toDto(model.getPosition()))
                .setCoverLetter(model.getCoverLetter())
                .setStatus(model.getStatus())
                .setResume(model.getResume())
                .setIsInvite(model.getIsInvite());
        setGenericFields(model, dto);
        return dto;
    }
}
