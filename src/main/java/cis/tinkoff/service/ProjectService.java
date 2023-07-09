package cis.tinkoff.service;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.support.exceptions.InaccessibleActionException;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    List<ProjectDTO> getAllUserProjects(String login, Boolean isLead);

    ProjectDTO getProjectById(Long id, String login);

    void deleteProjectById(Long id, String login) throws InaccessibleActionException;
}
