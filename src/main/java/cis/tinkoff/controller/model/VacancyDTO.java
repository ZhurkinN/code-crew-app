package cis.tinkoff.controller.model;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class VacancyDTO {
    private Long id;
    private DirectionDictionary direction;
    private String description;
    private List<String> skills;
    private Long createdWhen;
    private Boolean isVisible;
    private Project project;

    public static VacancyDTO toVacancyDTO(Position position) {
        if (position == null) {
            return null;
        }

        VacancyDTO vacancyDTO = VacancyDTO.builder()
                .id(position.getId())
                .direction(position.getDirection())
                .description(position.getDescription())
                .skills(position.getSkills())
                .createdWhen(position.getCreatedWhen().toEpochSecond(ZoneOffset.UTC))
                .isVisible(position.getIsVisible())
                .project(position.getProject()) //TODO use method from ProjectMapper class
                .build();

        return vacancyDTO;
    }

    public static List<VacancyDTO> toVacancyDTO(Collection<Position> positions) {
        if (positions == null) {
            return null;
        }

        List<VacancyDTO> vacancyDTOList = positions.stream()
                .map(VacancyDTO::toVacancyDTO)
                .toList();

        return vacancyDTOList;
    }
}
