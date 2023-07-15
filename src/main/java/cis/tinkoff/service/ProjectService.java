package cis.tinkoff.service;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.custom.ProjectCreateDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    List<ProjectDTO> getAllUserProjects(String login, Boolean isLead);

    ProjectDTO getProjectById(Long id, String login);

    void deleteProjectById(Long id, String login) throws InaccessibleActionException, RecordNotFoundException;

    void leaveUserFromProject(Long id, String login, Long newLeaderId) throws Exception;

    ProjectDTO deleteUserFromProject(Long id, String login, Long userId, Direction direction) throws RecordNotFoundException, InaccessibleActionException;

    ProjectDTO createProject(String login, ProjectCreateDTO projectCreateDTO) throws RecordNotFoundException;

    ProjectDTO updateProject(Long id, String login, ProjectDTO projectForUpdate) throws RecordNotFoundException, InaccessibleActionException;
}
