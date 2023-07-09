package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.User;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper;
import cis.tinkoff.support.mapper.PositionMapper;
import cis.tinkoff.support.mapper.ProjectMapper;
import cis.tinkoff.support.mapper.ProjectMemberDTOMapper;
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
    @Inject
    private final UserRepository userRepository;
    @Inject
    private final PositionRepository positionRepository;
    @Inject
    private final ProjectMapper projectMapper;
    @Inject
    private final PositionMapper positionMapper;
    @Inject
    private final ProjectMemberDTOMapper projectMemberDTOMapper;

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

    @Override
    public ProjectDTO getProjectById(Long id, String login) {
        Project project = projectRepository.findByIdInList(List.of(id)).get(0);

        List<Long> positionIds = project.getPositions().stream()
                .map(position -> position.getId())
                .toList();
        List<Position> positions = (List<Position>) positionRepository.findByIdInList(positionIds);

        List<Long> userIds = positions.stream()
                .filter(position -> position.getUser() != null)
                .map(position -> position.getUser().getId())
                .toList();
        List<User> users = userRepository.findByIdInList(userIds);

        ProjectDTO projectDTO = projectMapper.toProjectDTO(project);
        projectDTO.setMembers(projectMemberDTOMapper.toProjectMemberDTO(users, project.getPositions()));
        projectDTO.setVacanciesCount((int) project.getPositions().stream()
                .filter(position -> position.getUser() == null).count()
        );

        projectDTO.setIsLeader(project.getLeader().getEmail().equals(login));
        projectDTO.setMembersCount((int) projectDTO.getMembers().stream()
                .filter(projectMemberDTO -> projectMemberDTO != null)
                .count());

        return projectDTO;
    }

    @Override
    public void deleteProjectById(Long id, String login) throws InaccessibleActionException {
        Project project = projectRepository.findByIdInList(List.of(id)).get(0);

        if (project == null || project.getLeader().getEmail().equals(login)) {
            throw new InaccessibleActionException(ErrorDisplayMessageKeeper.PROJECT_WRONG_ACCESS);
        }

        projectRepository.softDeleteProject(id);

    }
}
