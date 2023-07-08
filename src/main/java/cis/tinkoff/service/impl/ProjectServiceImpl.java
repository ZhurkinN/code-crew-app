package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.support.mapper.ProjectMapper;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Primary
@Singleton
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<Project> getAll() {
        return projectRepository.list();
    }

    @Override
    public List<ProjectDTO> getAllUserProjects(String login, Boolean isLead) {
        List<Project> projects;
        if (isLead) {
            projects = projectRepository.findAllProjectsByLeadEmail(login);
        } else {
            projects = projectRepository.findAllByUserEmail(login);
        }

        List<ProjectDTO> projectDTOList = projectMapper.toProjectDTO(projects);

        return projectDTOList;
    }
}
