package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.controller.model.custom.ProjectCreateDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.repository.ProjectContactRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.service.*;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.INACCESSIBLE_PROJECT_ACTION;

@Singleton
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectContactRepository projectContactRepository;
    private final UserService userService;
    private final DictionaryService dictionaryService;
    private final PositionSupportService positionSupportService;
    private final ProjectSupportService projectSupportService;

    @Override
    public List<ProjectDTO> getAllUserProjects(String login, Boolean isLead) {
        List<Project> projects;
        if (isLead) {
            projects = projectRepository.findByLeaderEmailAndIsDeletedFalse(login);
        } else {
            projects = projectRepository.findByPositionsUserEmailAndIsDeletedFalse(login);
        }

        return ProjectDTO.toProjectDTO(projects, login);
    }

    @Override
    public ProjectDTO getProjectById(Long id, String login) {
        Project project = projectSupportService.getProjectByIdOrElseThrow(id);
        List<Position> positions = project.getPositions();
        List<User> users = positions.stream()
                .filter(position -> position.getUser() != null)
                .map(position -> position.getUser())
                .toList();

        return getProjectDTO(project, login);
    }

    @Override
    public void deleteProjectById(Long projectId,
                                  String email) {
        Project project = projectSupportService.getProjectByIdOrElseThrow(projectId);

        if (!projectSupportService.isUserProjectLeader(email, project.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    email,
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
        Project project = projectSupportService.getProjectByIdOrElseThrow(id);
        User oldUser = userService.getByEmail(login);

        positionSupportService
                .findPositionsByUserAndProjectOrElseThrow(
                        oldUser.getId(),
                        project.getId()
                );
        positionSupportService.softDeletePositionByUserIdAndProjectId(oldUser.getId(), project.getId());

        if (projectSupportService.isUserProjectLeader(login, project.getId())) {
            positionSupportService
                    .findPositionsByUserAndProjectOrElseThrow(
                            newLeaderId,
                            project.getId()
                    );
            User newLeader = userService.getById(newLeaderId);

            projectRepository.updateLeaderByLeaderId(project.getId(), newLeader);
        }
    }

    @Transactional
    @Override
    public ProjectDTO deleteUserFromProject(Long projectId,
                                            String login,
                                            Long userId,
                                            Direction direction) {
        Project project = projectSupportService.getProjectByIdOrElseThrow(projectId);

        if (!projectSupportService.isUserProjectLeader(login, project.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    login,
                    projectId
            );
        }

        positionSupportService
                .findPositionsByUserAndProjectOrElseThrow(
                        userId,
                        projectId
                );

        positionSupportService.softDeletePositionByUserIdAndProjectId(userId, projectId);

        project = projectSupportService.getProjectByIdOrElseThrow(projectId);

        return getProjectDTO(project, login);
    }

    @Transactional
    @Override
    public ProjectDTO createProject(String login,
                                    ProjectCreateDTO projectCreateDTO) {
        User leader = userService.getByEmail(login);
        ProjectStatusDictionary status = dictionaryService
                .getProjectStatusDictionaryById(projectCreateDTO.getStatus());
        DirectionDictionary directionDictionary = dictionaryService
                .getDirectionDictionaryById(projectCreateDTO.getDirection());

        Position newPosition = new Position().setUser(leader)
                .setDirection(directionDictionary)
                .setDescription(projectCreateDTO.getDescription())
                .setSkills(null)
                .setJoinDate(System.currentTimeMillis())
                .setIsVisible(false);

        Project newProject = new Project()
                .setLeader(leader)
                .setStatus(status)
                .setTitle(projectCreateDTO.getTitle())
                .setTheme(projectCreateDTO.getTheme())
                .setDescription(projectCreateDTO.getDescription())
                .setPositions(List.of(newPosition));

        if (projectCreateDTO.getContacts() != null) {
            List<ProjectContact> projectContactList = projectCreateDTO.getContacts().stream()
                    .map(contactDTO -> new ProjectContact().setLink(contactDTO.getLink()).setDescription(contactDTO.getDescription()))
                    .toList();

            newProject.setContacts(projectContactList);
        }

        newProject = projectRepository.save(newProject);

        return getProjectDTO(newProject, login);
    }

    @Override
    public ProjectDTO updateProject(Long projectId,
                                    String login,
                                    ProjectCreateDTO projectForUpdate) {
        Project updatedProject = projectSupportService.getProjectByIdOrElseThrow(projectId);

        if (!projectSupportService.isUserProjectLeader(login, updatedProject.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    login,
                    projectId
            );
        }

        List<ProjectContact> contacts = projectForUpdate.getContacts()
                .stream()
                .map(this::mapToProjectContactEntity)
                .toList();
        ProjectStatusDictionary status = dictionaryService
                .getProjectStatusDictionaryById(projectForUpdate.getStatus());

        updatedProject.setTitle(projectForUpdate.getTitle())
                .setTheme(projectForUpdate.getTheme())
                .setDescription(projectForUpdate.getDescription())
                .setContacts(contacts)
                .setStatus(status);

        projectContactRepository.deleteByProjectId(updatedProject.getId());
        projectRepository.update(updatedProject);

        updatedProject = projectSupportService.getProjectByIdOrElseThrow(projectId);

        return getProjectDTO(updatedProject, login);
    }

    private ProjectDTO getProjectDTO(Project project, String userLogin) {
        List<User> members = project.getPositions()
                .stream()
                .map(Position::getUser)
                .filter(Objects::nonNull)
                .toList();
        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project, userLogin);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(
                members,
                project.getPositions(),
                project.getLeader().getId()
        ));
        return projectDTO;
    }

    private ProjectContact mapToProjectContactEntity(ContactDTO contactDTO) {
        return new ProjectContact(null, contactDTO.getLink(), contactDTO.getDescription(), null);
    }
}
