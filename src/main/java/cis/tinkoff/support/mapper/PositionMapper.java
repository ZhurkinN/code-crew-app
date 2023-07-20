package cis.tinkoff.support.mapper;


import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.model.Position;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.List;

@Singleton
public class PositionMapper {
    public VacancyDTO toVacancyDTO(Position position) {
        if (position == null) {
            return null;
        }

        VacancyDTO vacancyDTO = VacancyDTO.builder()
                .id(position.getId())
                .direction(position.getDirection())
                .description(position.getDescription())
                .skills(position.getSkills())
                .createdWhen(position.getCreatedWhen())
//                .project(position.getProject()) //TODO use method from ProjectMapper class
                .build();

        return vacancyDTO;
    }

    public List<VacancyDTO> toVacancyDTO(Collection<Position> positions) {
        if (positions == null) {
            return null;
        }

        List<VacancyDTO> vacancyDTOList = positions.stream()
                .map(this::toVacancyDTO)
                .toList();

        return vacancyDTOList;
    }
}
