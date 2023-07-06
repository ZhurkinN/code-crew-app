package cis.tinkoff.service.impl;

import cis.tinkoff.model.Project;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.service.ProjectService;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Primary
@Singleton
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.list();
    }
}
