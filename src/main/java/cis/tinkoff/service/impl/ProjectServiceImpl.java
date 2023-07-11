package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.ProjectCreateDTO;
import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.ProjectMemberDTO;
import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.repository.dictionary.ProjectStatusRepository;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper;
import cis.tinkoff.support.mapper.PositionMapper;
import cis.tinkoff.support.mapper.ProjectMapper;
import cis.tinkoff.support.mapper.ProjectMemberDTOMapper;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
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
    private final ProjectStatusRepository projectStatusRepository;
    @Inject
    private final DirectionRepository directionRepository;
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

        List<ProjectDTO> projectDTOList = ProjectDTO.toProjectDTO(projects);

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

        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(users, project.getPositions()));
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
    public void deleteProjectById(Long id, String login) throws RecordNotFoundException, InaccessibleActionException {
        Project project = projectRepository.findByIdInList(List.of(id)).get(0);

        if (project == null) {
            throw new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND);
        }

        if (!project.getLeader().getEmail().equals(login)) {
            throw new InaccessibleActionException(ErrorDisplayMessageKeeper.PROJECT_WRONG_ACCESS);
        }

        projectRepository.softDeleteProject(id);

    }

    @Transactional
    @Override
    public void leaveUserFromProject(Long id, String login, Long newLeaderId) throws Exception {
        Project project = projectRepository.findByIdInList(List.of(id)).get(0);

        if (project == null) {
            throw new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND);
        }

        User oldUser = userRepository.findByEmail(login).orElseThrow(() -> new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND));
        List<Position> positions = positionRepository.findByUserIdAndProjectId(oldUser.getId(), project.getId());

        if (positions.size() == 0) {
            throw new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND);
        }

        if (project.getLeader().getEmail().equals(login)) {
//            projectRepository.updateLeaderByLeaderId(newLeaderId);
        }

        positions.forEach(position -> position.setUser(null));
        positionRepository.updateAll(positions);
    }

    @Transactional
    @Override
    public ProjectDTO deleteUserFromProject(Long id, String login, Long userId, Direction direction) throws RecordNotFoundException, InaccessibleActionException {
        Project project = projectRepository.findByIdInList(List.of(id)).get(0);

        if (project == null) {
            throw new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND);
        }

        if (!project.getLeader().getEmail().equals(login)) {
            throw new InaccessibleActionException(ErrorDisplayMessageKeeper.PROJECT_WRONG_ACCESS);
        }

        List<Long> positionIds = project.getPositions().stream()
                .map(position -> position.getId())
                .toList();
        List<Position> positions = (List<Position>) positionRepository.findByIdInList(positionIds);
        positions = positions.stream().filter(position -> position.getUser().getId() != userId ||
                !position.getDirection().getDirectionName().equals(direction)
        ).toList();

        positionRepository.updateAll(positions);

        List<Long> userIds = positions.stream()
                .filter(position -> position.getUser() != null)
                .map(position -> position.getUser().getId())
                .toList();
        List<User> users = userRepository.findByIdInList(userIds);

        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(users, project.getPositions()));
        projectDTO.setVacanciesCount((int) project.getPositions().stream()
                .filter(position -> position.getUser() == null).count()
        );

        projectDTO.setIsLeader(project.getLeader().getEmail().equals(login));
        projectDTO.setMembersCount((int) projectDTO.getMembers().stream()
                .filter(projectMemberDTO -> projectMemberDTO != null)
                .count());

        return projectDTO;
    }

    @Transactional
    @Override
    public Long createProject(String login, ProjectCreateDTO projectCreateDTO) throws RecordNotFoundException {
        User leader = userRepository.findByEmail(login)
                .orElseThrow(() -> new RecordNotFoundException("user not found"));
        ProjectStatusDictionary status = projectStatusRepository.findById(projectCreateDTO.getStatus())
                .orElseThrow(() -> new RecordNotFoundException("status not found"));
        DirectionDictionary direction = directionRepository.findById(projectCreateDTO.getDirection())
                .orElseThrow(() -> new RecordNotFoundException("direction not found"));

        Project newProject = new Project();

        newProject.setLeader(leader);
        newProject.setStatus(status);
        newProject.setTitle(projectCreateDTO.getTitle());
        newProject.setTheme(projectCreateDTO.getTheme());
        newProject.setDescription(projectCreateDTO.getDescription());

        newProject = projectRepository.save(newProject);

        Position newPosition = new Position();

        newPosition.setUser(leader);
        newPosition.setProject(newProject);
        newPosition.setDirection(direction);
        newPosition.setDescription("leader of the project");
        newPosition.setIsVisible(false);

        newPosition = positionRepository.save(newPosition);

        newProject.setPositions(List.of(newPosition));

        if (projectCreateDTO.getContacts() != null || projectCreateDTO.getContacts().size() != 0) {
            List<ProjectContact> projectContactList = projectCreateDTO.getContacts().stream()
                    .map(contactDTO -> new ProjectContact().setLink(contactDTO.getLink()).setDescription(contactDTO.getDescription()))
                    .toList();

            for (ProjectContact projectContact : projectContactList) {
                projectContact.setProject(newProject);
            }

            newProject.setContacts(projectContactList);
        }

        projectRepository.update(newProject);

        return newProject.getId();
    }
}
