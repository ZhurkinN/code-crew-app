package cis.tinkoff.controller.model;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.dictionary.DirectionDictionary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacancyDTO {

    private Long id;
    private DirectionDictionary direction;
    private String description;
    private List<String> skills;
    private Long createdWhen;
    private Boolean isVisible;
    private ProjectDTO project;

    public static VacancyDTO toVacancyDTO(Position position) {
        if (position == null) {
            return null;
        }

        return VacancyDTO.builder()
                .id(position.getId())
                .direction(position.getDirection())
                .description(position.getDescription())
                .skills(position.getSkills())
                .createdWhen(position.getCreatedWhen())
                .isVisible(position.getIsVisible())
                .project(ProjectDTO.toProjectDTO(position.getProject(), ""))
                .build();
    }

    public static List<VacancyDTO> toVacancyDTO(Collection<Position> positions) {
        if (positions == null) {
            return null;
        }

        return positions.stream()
                .map(VacancyDTO::toVacancyDTO)
                .toList();
    }

    public static void updateEntityByDTO(Position position, VacancyDTO vacancyDTO) {
        position.setIsVisible(vacancyDTO.getIsVisible() == null ? position.getIsVisible() : vacancyDTO.getIsVisible());
        position.setDirection(vacancyDTO.getDirection() == null ? position.getDirection() : vacancyDTO.getDirection());
        position.setDescription(vacancyDTO.getDescription() == null ? position.getDescription() : vacancyDTO.getDescription());
        position.setSkills(vacancyDTO.getSkills() == null ? position.getSkills() : vacancyDTO.getSkills());
    }
}
