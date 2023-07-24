package cis.tinkoff.controller.model;

import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.ProjectStatusDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude
public class ProjectDTO {
    private Long id;
    private Boolean isLeader;
    private String title;
    private String theme;
    private String description;
    private Integer membersCount;
    private ProjectStatusDictionary status;
    private List<ContactDTO> contacts;
    private Integer vacanciesCount;
    private List<ProjectMemberDTO> members;
    private Long createdWhen;

    public static ProjectDTO toProjectDTO(Project project, String userLogin) { // почему методы-мапперы в дто? напиши маппер
        // отдельно, а лучше генирируй
        if (project == null) {
            return null;
        }

        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .theme(project.getTheme())
                .description(project.getDescription())
                .status(project.getStatus())
                .contacts(ContactDTO.toContactDto(project.getContacts()))
                .createdWhen(project.getCreatedWhen())
                .isLeader(project.getLeader().getEmail().equals(userLogin))
                .build();

        List<Position> positions = project.getPositions();

        if (positions != null) {
            int vacanciesCount = Math.toIntExact(positions.stream().filter(position -> position.getUser() == null).count());
            Integer membersCount = positions.size() - vacanciesCount;
            projectDTO.setVacanciesCount(vacanciesCount);
            projectDTO.setMembersCount(membersCount);
        }

        return projectDTO;
    }

    public static List<ProjectDTO> toProjectDTO(Collection<Project> projects, String userLogin) {
        if (projects == null) {
            return null;
        }

        List<ProjectDTO> projectDTOList = projects.stream()
                .map(project -> ProjectDTO.toProjectDTO(project, userLogin))
                .toList();

        return projectDTOList;
    }
}
