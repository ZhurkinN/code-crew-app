package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.List;

@Singleton
public class ProjectMapper {

    public ProjectDTO toProjectDTO(Project project, String userLogin) {
        if (project == null) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO()
                .setId(project.getId())
                .setTitle(project.getTitle())
                .setTheme(project.getTheme())
                .setDescription(project.getDescription())
                .setStatus(project.getStatus())
                .setContacts(ContactDTO.toContactDto(project.getContacts()))
                .setCreatedWhen(project.getCreatedWhen())
                .setIsLeader(project.getLeader().getEmail().equals(userLogin));

        List<Position> positions = project.getPositions();

        if (positions != null) {
            int vacanciesCount = Math.toIntExact(positions.stream().filter(position -> position.getUser() == null).count());
            Integer membersCount = positions.size() - vacanciesCount;
            projectDTO.setVacanciesCount(vacanciesCount);
            projectDTO.setMembersCount(membersCount);
        }

        return projectDTO;
    }

    public List<ProjectDTO> toProjectDTO(Collection<Project> projects, String userLogin) {
        if (projects == null) {
            return null;
        }

        return projects.stream()
                .map(project -> toProjectDTO(project, userLogin))
                .toList();
    }
}
