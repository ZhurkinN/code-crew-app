package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.model.Project;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.List;

@Singleton
public class ProjectMapper {
    public ProjectDTO toProjectDTO(Project project) {
        if (project == null) {
            return null;
        }

        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .theme(project.getTheme())
                .description(project.getDescription())
                .status(project.getStatus())
//                .contacts(project.getContacts())
                .build();

        return projectDTO;
    }

    public List<ProjectDTO> toProjectDTO(Collection<Project> projects) {
        if (projects == null) {
            return null;
        }

        List<ProjectDTO> projectDTOList = projects.stream()
                .map(this::toProjectDTO)
                .toList();

        return projectDTOList;
    }
}
