package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.request.VacancyRequestDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;

import java.util.Objects;

@Singleton
public class VacancyMapper extends GenericMapper<Position, VacancyRequestDTO> {
    @Override
    public VacancyRequestDTO toDto(Position model) {
        if (Objects.isNull(model)) {
            return null;
        }

        VacancyRequestDTO dto = new VacancyRequestDTO()
                .setDescription(model.getDescription())
                .setProject(model.getProject())
                .setDirection(model.getDirection())
                .setSkills(model.getSkills());
        setGenericFields(model, dto);
        return dto;
    }
}
