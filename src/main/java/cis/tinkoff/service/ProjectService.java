package cis.tinkoff.service;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.custom.ProjectCreateDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.enumerated.Direction;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllUserProjects(String login, Boolean isLead);

    ProjectDTO getProjectById(Long id, String login);

    void deleteProjectById(Long id, String login);

    void leaveUserFromProject(Long id, String login, Long newLeaderId);

    ProjectDTO deleteUserFromProject(Long id, String login, Long userId, Direction direction);

    ProjectDTO createProject(String login, ProjectCreateDTO projectCreateDTO);

    ProjectDTO updateProject(Long id, String login, ProjectCreateDTO projectForUpdate);

    boolean isUserProjectLeader(String login, Long projectId);

    List<Project> getAllProjectsByIdsOrElseThrow(List<Long> ids);
}
