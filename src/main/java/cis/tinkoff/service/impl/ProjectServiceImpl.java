package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.controller.model.custom.ProjectCreateDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.ProjectContactRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.PositionService;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.INACCESSIBLE_PROJECT_ACTION;

@Primary
@Singleton
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final PositionRepository positionRepository;
    private final ProjectContactRepository projectContactRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final DictionaryService dictionaryService;
    @Inject
    private Provider<PositionService> positionService;

    @Override
    public List<Project> getAll() {
        return projectRepository.list();
    }

    @Override
    public List<ProjectDTO> getAllUserProjects(String login, Boolean isLead) {
        List<Project> projects;
        if (isLead) {
            projects = projectRepository.findByLeaderEmailAndIsDeletedFalse(login);
        } else {
            projects = projectRepository.findByPositionsUserEmailAndIsDeletedFalse(login);
        }

        List<ProjectDTO> projectDTOList = ProjectDTO.toProjectDTO(projects);
        projectDTOList.forEach(projectDTO -> projectDTO.setIsLeader(
                isUserProjectLeader(login, projectDTO.getId())
        ));

        return projectDTOList;
    }

    @Override
    public ProjectDTO getProjectById(Long id, String login) {
        Project project = getAllProjectsByIdsOrElseThrow(List.of(id)).get(0);

        List<Long> positionIds = project.getPositions().stream()
                .map(GenericModel::getId)
                .toList();
        List<Position> positions = positionService.get().findPositionsByIdsOrElseThrow(positionIds);

        if (positions.size() == 0) {
            throw new RecordNotFoundException("Position with id=" + positionIds + " not found");
        }

        List<Long> userIds = positions.stream()
                .filter(position -> position.getUser() != null)
                .map(position -> position.getUser().getId())
                .toList();
        List<User> users = userService.findUsersByIdsOrElseThrow(userIds);

        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(users, positions, project.getLeader().getId()));
        projectDTO.setVacanciesCount((int) project.getPositions().stream()
                .filter(position -> position.getUser() == null).count()
        );

        projectDTO.setIsLeader(isUserProjectLeader(login, project.getId()));
        projectDTO.setMembersCount((int) projectDTO.getMembers().stream()
                .filter(Objects::nonNull)
                .count());

        return projectDTO;
    }

    @Override
    public void deleteProjectById(Long projectId,
                                  String email) throws RecordNotFoundException, InaccessibleActionException {
        Project project = getAllProjectsByIdsOrElseThrow(List.of(projectId)).get(0);

        if (!isUserProjectLeader(email, project.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    userRepository.findIdByEmail(email),
                    projectId
            );
        }

        projectRepository.softDeleteProject(projectId);

    }

    @Transactional
    @Override
    public void leaveUserFromProject(Long id,
                                     String login,
                                     Long newLeaderId) {
        Project project = getAllProjectsByIdsOrElseThrow(List.of(id)).get(0);

        User oldUser = userService.getByEmail(login);
        List<Position> positions = positionService.get()
                .findPositionsByUserAndProjectAndDirectionOrElseThrow(
                        oldUser.getId(),
                        project.getId(),
                        null
                );

        positions.forEach(position -> position.setUser(null).setIsDeleted(true));
        positionRepository.updateAll(positions);

        if (isUserProjectLeader(login, project.getId())) {
            positionService.get()
                    .findPositionsByUserAndProjectAndDirectionOrElseThrow(
                            newLeaderId,
                            project.getId(),
                            null
                    );
            User newLeader = userService.getById(newLeaderId);

            projectRepository.updateLeaderByLeaderId(project.getId(), newLeader);
        }
    }

    @Transactional
    @Override
    public ProjectDTO deleteUserFromProject(Long projectId,
                                            String email,
                                            Long userId,
                                            Direction direction) throws RecordNotFoundException, InaccessibleActionException {
        Project project = getAllProjectsByIdsOrElseThrow(List.of(projectId)).get(0);

        if (!isUserProjectLeader(email, project.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    userRepository.findIdByEmail(email),
                    projectId
            );
        }

        List<Position> positions = positionService.get()
                .findPositionsByUserAndProjectAndDirectionOrElseThrow(
                        userId,
                        projectId,
                        direction
                );
        positions.forEach(position -> position.setUser(null).setIsDeleted(true));

        positionRepository.updateAll(positions);

        List<User> members = project.getPositions()
                .stream()
                .map(Position::getUser)
                .toList();
        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(
                members,
                project.getPositions(),
                project.getLeader().getId()
        ));

        projectDTO = getProjectDTO(project);
        projectDTO.setIsLeader(isUserProjectLeader(email, project.getId()));

        return projectDTO;
    }

    @Transactional
    @Override
    public ProjectDTO createProject(String login,
                                    ProjectCreateDTO projectCreateDTO) throws RecordNotFoundException {
        User leader = userService.getByEmail(login);
        ProjectStatusDictionary status = dictionaryService
                .getProjectStatusDictionaryById(projectCreateDTO.getStatus());

        Project newProject = new Project();

        newProject.setLeader(leader);
        newProject.setStatus(status);
        newProject.setTitle(projectCreateDTO.getTitle());
        newProject.setTheme(projectCreateDTO.getTheme());
        newProject.setDescription(projectCreateDTO.getDescription());

        newProject = projectRepository.save(newProject);

        Position newPosition = positionService.get()
                .createPosition(
                        leader,
                        newProject,
                        projectCreateDTO.getDirection(),
                        "leader of the project",
                        null,
                        System.currentTimeMillis(),
                        false
                );
        List<Position> newPositions = positionService.get().saveAllPositions(List.of(newPosition));

//        Position newPosition = new Position();
//
//        newPosition.setUser(leader);
//        newPosition.setProject(newProject);
//        newPosition.setDirection(direction);
//        newPosition.setDescription("leader of the project");
//        newPosition.setJoinDate(System.currentTimeMillis());
//        newPosition.setIsVisible(false);
//
//        newPosition = positionRepository.save(newPosition);

        newProject.setPositions(newPositions);

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

        ProjectDTO projectDTO = getProjectDTO(newProject);
        projectDTO.setIsLeader(true);

        return projectDTO;
    }

    @Override
    public ProjectDTO updateProject(Long projectId,
                                    String email,
                                    ProjectCreateDTO projectForUpdate) throws RecordNotFoundException, InaccessibleActionException {
        Project updatedProject = getAllProjectsByIdsOrElseThrow(List.of(projectId)).get(0);

        if (!isUserProjectLeader(email, updatedProject.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    userRepository.findIdByEmail(email),
                    projectId
            );
        }

        List<ProjectContact> contacts = projectForUpdate.getContacts()
                .stream()
                .map(this::mapToProjectContactEntity)
                .toList();
        ProjectStatusDictionary status = dictionaryService
                .getProjectStatusDictionaryById(projectForUpdate.getStatus());

        updatedProject.setTitle(projectForUpdate.getTitle());
        updatedProject.setTheme(projectForUpdate.getTheme());
        updatedProject.setDescription(projectForUpdate.getDescription());
        updatedProject.setContacts(contacts);
        updatedProject.setStatus(status);

        projectContactRepository.deleteByProjectId(updatedProject.getId());
        projectRepository.update(updatedProject);

        updatedProject = projectRepository.findByIdInList(List.of(projectId)).get(0);

        ProjectDTO projectDTO = getProjectDTO(updatedProject);
        projectDTO.setIsLeader(true);

        return projectDTO;
    }

    private ProjectDTO getProjectDTO(Project project) {
        List<User> members = project.getPositions()
                .stream()
                .map(Position::getUser)
                .filter(Objects::nonNull)
                .toList();
        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(
                members,
                project.getPositions(),
                project.getLeader().getId()
        ));
        return projectDTO;
    }

    @Override
    public boolean isUserProjectLeader(String login, Long projectId) {
        List<Project> projects = getAllProjectsByIdsOrElseThrow(List.of(projectId));

        Project project = projects.get(0);

        return project.getLeader().getEmail().equals(login);
    }

    @Override
    public List<Project> getAllProjectsByIdsOrElseThrow(List<Long> ids) {
        List<Project> projects = projectRepository.findByIdInList(ids);

        if (projects.size() == 0) {
            throw new RecordNotFoundException("Projects with ids=" + ids.toString() + " not found");
        }

        return projects;
    }

    public Project createProject(User leader, ProjectStatus status, String title, String theme, String description) {
        return null;
    }

    private ProjectContact mapToProjectContactEntity(ContactDTO contactDTO) {
        return new ProjectContact(null, contactDTO.getLink(), contactDTO.getDescription(), null);
    }
}
