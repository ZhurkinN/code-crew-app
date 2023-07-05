package cis.tinkoff.service.impl;

import cis.tinkoff.model.Project;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.service.ProjectService;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Primary
@Singleton
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.list();
    }
}
