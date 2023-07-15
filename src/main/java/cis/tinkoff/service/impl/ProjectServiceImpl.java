package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.custom.ProjectCreateDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.ProjectContactRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.repository.dictionary.ProjectStatusRepository;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper;
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
    private final ProjectContactRepository projectContactRepository;
    @Inject
    private final DirectionRepository directionRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.list();
    }

    @Override
    public List<ProjectDTO> getAllUserProjects(String login, Boolean isLead) {
        List<Project> projects;
        if (isLead) {
            projects = projectRepository.findByLeaderEmail(login);
        } else {
            projects = projectRepository.findByPositionsUserEmail(login);
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
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(users, positions, project.getLeader().getId()));
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

        positions.forEach(position -> position.setUser(null));
        positionRepository.updateAll(positions);

        if (project.getLeader().getEmail().equals(login)) {
            if (positionRepository.findByUserIdAndProjectId(newLeaderId, project.getId()).size() == 0) {
                throw new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND);
            }
            User newLeader = userRepository.findById(newLeaderId)
                    .orElseThrow(() -> new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND));
            projectRepository.updateLeaderByLeaderId(project.getId(), newLeader);
        }
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
        positions.stream().filter(position -> position.getUser().getId() == userId &&
                position.getDirection().getDirectionName().equals(direction)
        ).forEach(position -> position.setIsDeleted(true));

        positionRepository.updateAll(positions);

        List<Long> userIds = positions.stream()
                .filter(position -> position.getUser() != null && !position.getIsDeleted())
                .map(position -> position.getUser().getId())
                .toList();
        List<User> users = userRepository.findByIdInList(userIds);

        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(users, positions, project.getLeader().getId()));
        projectDTO.setVacanciesCount((int) positions.stream()
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
    public ProjectDTO createProject(String login, ProjectCreateDTO projectCreateDTO) throws RecordNotFoundException {
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

        return ProjectDTO.toProjectDTO(newProject);
    }

    @Override
    public ProjectDTO updateProject(Long id, String login, Project projectForUpdate) throws RecordNotFoundException, InaccessibleActionException {
        List<Project> projects = projectRepository.findByIdInList(List.of(id));

        if (projects.size() == 0) {
            throw new RecordNotFoundException("project whith " + id + " not found");
        }

        Project updatedProject = projects.get(0);

        if (!updatedProject.getLeader().getEmail().equals(login)) {
            throw new InaccessibleActionException(ErrorDisplayMessageKeeper.PROJECT_WRONG_ACCESS);
        }

        List<ProjectContact> contacts = projectForUpdate.getContacts();
        ProjectStatusDictionary status = projectStatusRepository.findById(projectForUpdate.getStatus().getStatusName())
                .orElseThrow(() -> new RecordNotFoundException("status " + projectForUpdate.getStatus().getStatusName() + " not found"));

        updatedProject.setTitle(projectForUpdate.getTitle());
        updatedProject.setTheme(projectForUpdate.getTheme());
        updatedProject.setDescription(projectForUpdate.getDescription());
        updatedProject.setContacts(contacts);
        updatedProject.setStatus(status);

        projectContactRepository.deleteByProjectId(updatedProject.getId());
        projectRepository.update(updatedProject);

        return ProjectDTO.toProjectDTO(updatedProject);
    }
}
