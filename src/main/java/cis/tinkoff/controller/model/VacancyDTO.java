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
    private ProjectDTO project;

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
                .project(ProjectDTO.toProjectDTO(position.getProject())) //TODO use method from ProjectMapper class
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

    public static void updateEntityByDTO(Position position, VacancyDTO vacancyDTO) {
//        position.setId(vacancyDTO.getId() == null ? position.getId() : vacancyDTO.getId());
//        position.setIsDeleted(vacancyDTO.get() == null ? position.getId() : vacancyDTO.getId());
        position.setIsVisible(vacancyDTO.getIsVisible() == null ? position.getIsVisible() : vacancyDTO.getIsVisible());
        position.setDirection(vacancyDTO.getDirection() == null ? position.getDirection() : vacancyDTO.getDirection());
        position.setDescription(vacancyDTO.getDescription() == null ? position.getDescription() : vacancyDTO.getDescription());
        position.setSkills(vacancyDTO.getSkills() == null ? position.getSkills() : vacancyDTO.getSkills());
//        position.setUser(vacancyDTO.get() == null ? position.getId() : vacancyDTO.getId());
//        position.setProject(vacancyDTO.getProject() == null ? position.getProject() : vacancyDTO.getProject());
//        position.setCreatedWhen(vacancyDTO.getCreatedWhen() == null ? position.getCreatedWhen() : vacancyDTO.getCreatedWhen()); //TODO waiting for cast time to long
    }
}
