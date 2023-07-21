package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.controller.model.custom.ProjectCreateDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.ProjectContactRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.service.*;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.INACCESSIBLE_PROJECT_ACTION;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.PROJECT_NOT_FOUND;

@Singleton
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final PositionRepository positionRepository;
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

        List<ProjectDTO> projectDTOList = ProjectDTO.toProjectDTO(projects);

        projectDTOList.forEach(projectDTO -> projectDTO.setIsLeader(
                projectSupportService.isUserProjectLeader(login, projectDTO.getId())
        ));

        return projectDTOList;
    }

    @Override
    public ProjectDTO getProjectById(Long id, String login) {
        Project project = projectSupportService.getProjectByIdOrElseThrow(id);
        List<Position> positions = project.getPositions();
        List<User> users =positions.stream()
                .filter(position -> position.getUser() != null)
                .map(position -> position.getUser())
                .toList();

        ProjectDTO projectDTO = ProjectDTO.toProjectDTO(project);
        projectDTO.setMembers(ProjectMemberDTO.toProjectMemberDTO(users, positions, project.getLeader().getId()));
        projectDTO.setVacanciesCount((int) project.getPositions().stream()
                .filter(position -> position.getUser() == null).count()
        );
        projectDTO.setIsLeader(projectSupportService.isUserProjectLeader(login, project.getId()));
        projectDTO.setMembersCount((int) projectDTO.getMembers().stream()
                .filter(Objects::nonNull)
                .count());

        return projectDTO;
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
        positionRepository.softDeletePositionByUserIdAndProjectId(oldUser.getId(), project.getId());

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
                                            String email,
                                            Long userId,
                                            Direction direction) {
        Project project = projectSupportService.getProjectByIdOrElseThrow(projectId);

        if (!projectSupportService.isUserProjectLeader(email, project.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    email,
                    projectId
            );
        }

        positionSupportService
                .findPositionsByUserAndProjectOrElseThrow(
                        userId,
                        projectId
                );

        positionRepository.softDeletePositionByUserIdAndProjectId(userId, projectId);

        project = projectSupportService.getProjectByIdOrElseThrow(projectId);

        ProjectDTO projectDTO = getProjectDTO(project);
        projectDTO.setIsLeader(projectSupportService.isUserProjectLeader(email, project.getId()));

        return projectDTO;
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

        if (projectCreateDTO.getContacts() != null || projectCreateDTO.getContacts().size() != 0) {
            List<ProjectContact> projectContactList = projectCreateDTO.getContacts().stream()
                    .map(contactDTO -> new ProjectContact().setLink(contactDTO.getLink()).setDescription(contactDTO.getDescription()))
                    .toList();

            newProject.setContacts(projectContactList);
        }

        newProject = projectRepository.save(newProject);

        ProjectDTO projectDTO = getProjectDTO(newProject);
        projectDTO.setIsLeader(true);

        return projectDTO;
    }

    @Override
    public ProjectDTO updateProject(Long projectId,
                                    String email,
                                    ProjectCreateDTO projectForUpdate) {
        Project updatedProject = projectSupportService.getProjectByIdOrElseThrow(projectId);

        if (!projectSupportService.isUserProjectLeader(email, updatedProject.getId())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    email,
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

//    @Override
//    public boolean isUserProjectLeader(String login, Long projectId) {
//        Project project = getProjectByIdsOrElseThrow(projectId);
//
//        return project.getLeader().getEmail().equals(login);
//    }
//
//    @Override
//    public Project getProjectByIdsOrElseThrow(Long id) {
//
//        return projectRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException(
//                        PROJECT_NOT_FOUND,
//                        id
//                ));
//    }

    private ProjectContact mapToProjectContactEntity(ContactDTO contactDTO) {
        return new ProjectContact(null, contactDTO.getLink(), contactDTO.getDescription(), null);
    }
}
