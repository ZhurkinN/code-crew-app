package cis.tinkoff.controller.model;

import cis.tinkoff.model.Project;
import cis.tinkoff.model.ProjectContact;
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

    public static ProjectDTO toProjectDTO(Project project) {
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
                .build();

        return projectDTO;
    }

    public static List<ProjectDTO> toProjectDTO(Collection<Project> projects) {
        if (projects == null) {
            return null;
        }

        List<ProjectDTO> projectDTOList = projects.stream()
                .map(ProjectDTO::toProjectDTO)
                .toList();

        return projectDTOList;
    }
}
