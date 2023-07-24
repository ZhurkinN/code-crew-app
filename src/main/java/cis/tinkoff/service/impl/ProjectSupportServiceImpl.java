package cis.tinkoff.service.impl;

import cis.tinkoff.model.Project;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.service.ProjectSupportService;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.PROJECT_NOT_FOUND;

@Singleton
@RequiredArgsConstructor
public class ProjectSupportServiceImpl implements ProjectSupportService {

    private final ProjectRepository projectRepository;

    @Override
    public boolean isUserProjectLeader(String login, Long projectId) {
        Project project = getProjectByIdOrElseThrow(projectId); // зачем эта переменная?

        return project.getLeader().getEmail().equals(login);
    }

    @Override
    public Project getProjectByIdOrElseThrow(Long id) {

        return projectRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        PROJECT_NOT_FOUND,
                        id
                ));
    }
}
