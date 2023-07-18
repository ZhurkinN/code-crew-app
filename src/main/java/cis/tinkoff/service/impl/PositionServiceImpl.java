package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.controller.model.custom.VacancyCreateDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import cis.tinkoff.model.enumerated.SortDirection;
import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.PositionService;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Primary
@Singleton
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final ProjectService projectService;
    private final DictionaryService dictionaryService;

    @Override
    public SearchDTO searchVacancyList(Integer page,
                                       Integer sizeLimit,
                                       SortDirection dateSort,
                                       String status,
                                       String direction,
                                       String skills) {
        String projectStatus = null;
        String vacancyDirection = null;
        List<String> skillList = null;

        if (status != null) {
            projectStatus = ProjectStatus.valueOf(status).toString();
        }
        if (direction != null) {
            vacancyDirection = Direction.valueOf(direction).toString();
        }
        if (skills != null) {
            skillList = Arrays.stream(skills.split(" ")).toList();
        }

        Sort.Order sortOrder = new Sort.Order("createdWhen");
        if (dateSort != null) {
            Sort.Order.Direction sortDirection = Sort.Order.Direction.valueOf(dateSort.name());
            sortOrder = new Sort.Order("createdWhen", sortDirection, false);
        }

        Pageable pageable = Pageable.from(page, sizeLimit, Sort.of(sortOrder));

        Page<Position> positionPage = positionRepository.searchAllVacancies(
                vacancyDirection,
                projectStatus,
                skillList,
                pageable
        );

        List<Long> positionIds = positionPage
                .getContent()
                .stream()
                .map(GenericModel::getId)
                .toList();

        List<Position> positions = positionRepository.findByIdInList(
                positionIds,
                Sort.of(sortOrder)
        );

        return SearchDTO.toDto(
                VacancyDTO.toVacancyDTO(positions),
                positionPage.getTotalPages()
        );
    }

    @Override
    public List<Position> getAll() {
        return (List<Position>) positionRepository.findAll();
    }

    @Override
    public VacancyDTO getVacancyById(Long id) {
        List<Position> positions = findPositionsByIdsOrElseThrow(List.of(id));

        Position position = positions.get(0);

        if (!position.getIsVisible() || position.getUser() != null) {
            throw new InaccessibleActionException("Vacancy with id=" + id + " is inaccessible");
        }

        return VacancyDTO.toVacancyDTO(position);
    }

    @Override
    public List<VacancyDTO> getProjectVacancies(Long projectId, Boolean isVisible) {
        List<Position> positions = positionRepository.findByProjectIdAndIsDeletedFalse(projectId, isVisible);

        return VacancyDTO.toVacancyDTO(positions);
    }

    @Override
    public VacancyDTO createVacancy(String login, Long projectId, VacancyCreateDTO vacancyCreateDTO) {
        DirectionDictionary directionDictionary = dictionaryService
                .getDirectionDictionaryById(vacancyCreateDTO.getDirection());

        if (!projectService.isUserProjectLeader(login, projectId)) {
            throw new InaccessibleActionException("Actions whit project witch id=" + projectId + " is inaccessible");
        }

        List<Project> projects = projectService.getAllProjectsByIdsOrElseThrow(List.of(projectId));

        Project project = projects.get(0);

        Position newPosition = new Position();

        newPosition.setDirection(directionDictionary);
        newPosition.setDescription(vacancyCreateDTO.getDescription());
        newPosition.setSkills(vacancyCreateDTO.getSkills());
        newPosition.setUser(null);
        newPosition.setCreatedWhen(System.currentTimeMillis());
        newPosition.setIsDeleted(false);
        newPosition.setIsVisible(true);
        newPosition.setProject(project);

        newPosition = positionRepository.save(newPosition);

        return VacancyDTO.toVacancyDTO(newPosition);
    }

    @Override
    public VacancyDTO updateVacancy(Long id, String login, VacancyCreateDTO updateVacancyDTO) {
        DirectionDictionary directionDictionary = dictionaryService
                .getDirectionDictionaryById(updateVacancyDTO.getDirection());

        List<Position> positions = findPositionsByIdsOrElseThrow(List.of(id));

        Position updatedPosition = positions.get(0);

        List<Project> projects = projectService.getAllProjectsByIdsOrElseThrow(List.of(updatedPosition.getProject().getId()));

        if (!projectService.isUserProjectLeader(login, projects.get(0).getId())) {
            throw new InaccessibleActionException("Actions whit project witch id=" +
                    projects.get(0).getId()
                    + " is inaccessible");
        }

        updatedPosition.setDirection(directionDictionary);
        updatedPosition.setDescription(updateVacancyDTO.getDescription());
        updatedPosition.setSkills(updateVacancyDTO.getSkills());

        updatedPosition = positionRepository.update(updatedPosition);

        return VacancyDTO.toVacancyDTO(updatedPosition);
    }

    @Override
    public VacancyDTO changeVisibility(Long id, String login) {
        List<Position> positions = findPositionsByIdsOrElseThrow(List.of(id));

        Position updatedPosition = positions.get(0);

        List<Project> projects = projectService.getAllProjectsByIdsOrElseThrow(List.of(updatedPosition.getProject().getId()));

        if (!projectService.isUserProjectLeader(login, projects.get(0).getId())) {
            throw new InaccessibleActionException("Actions whit project witch id=" +
                    projects.get(0).getId()
                    + " is inaccessible");
        }

        updatedPosition.setIsVisible(!updatedPosition.getIsVisible());

        updatedPosition = positionRepository.update(updatedPosition);

        return VacancyDTO.toVacancyDTO(updatedPosition);
    }

    @Override
    public void deleteVacancy(Long id, String login) {
        List<Position> positions = findPositionsByIdsOrElseThrow(List.of(id));

        Position updatedPosition = positions.get(0);

        List<Project> projects = projectService.getAllProjectsByIdsOrElseThrow(List.of(updatedPosition.getProject().getId()));

        if (!projectService.isUserProjectLeader(login, projects.get(0).getId())) {
            throw new InaccessibleActionException("Actions whit project witch id=" +
                    projects.get(0).getId()
                    + " is inaccessible");
        }

        updatedPosition.setIsDeleted(true);
        positionRepository.update(updatedPosition);
    }

    @Override
    public List<ProjectMemberDTO> getProjectMembers(String login, Long projectId) {
        if (!isUserProjectMember(login, projectId)) {
            throw new InaccessibleActionException("Actions whit project witch id=" +
                    projectId + " is inaccessible");
        }

        List<Position> positions = positionRepository.retrieveByProjectId(projectId);
        List<User> members = positions.stream()
                .map(Position::getUser)
                .filter(Objects::nonNull)
                .toList();
        User leader = positions.get(0).getProject().getLeader();

        return ProjectMemberDTO.toProjectMemberDTO(members, positions, leader.getId());
    }

    @Override
    public boolean isUserProjectMember(String login, Long projectId) {
        List<Position> members = positionRepository.findByProjectIdAndUserEmail(projectId, login);

        if (members.size() == 0) {
            throw new RecordNotFoundException("There is no user with login = " + login +
                    " in project with id=" + projectId);
        }

        return true;
    }

    @Override
    public List<Position> findPositionsByIdsOrElseThrow(List<Long> ids) {
        List<Position> positions = positionRepository.findByIdInList(ids, Sort.UNSORTED);

        if (positions.size() == 0) {
            throw new RecordNotFoundException("Position with id=" + ids.toString() + " not found");
        }

        return positions;
    }

    @Override
    public List<Position> findPositionsByUserAndProjectAndDirectionOrElseThrow(Long userId, Long projectId, Direction direction) {
        List<Position> positions;
        if (Objects.nonNull(direction)) {
            positions = positionRepository.findByUserIdAndProjectIdAndDirectionDirectionName(userId, projectId, direction);
        } else {
            positions = positionRepository.findByUserIdAndProjectId(userId, projectId);
        }

        if (positions.size() == 0) {
            throw new RecordNotFoundException(ErrorDisplayMessageKeeper.RECORD_NOT_FOUND);
        }

        return positions;
    }

    @Override
    public Position createPosition(@NonNull User user, @NonNull Project project, @NonNull Direction direction, String description, List<String> skills, Long joinDate, Boolean isVisible) {
        DirectionDictionary directionDictionary = dictionaryService
                .getDirectionDictionaryById(direction);
        Position newPosition = new Position();

        newPosition.setUser(user);
        newPosition.setProject(project);
        newPosition.setDirection(directionDictionary);
        newPosition.setDescription(description);
        newPosition.setJoinDate(joinDate);
        newPosition.setIsVisible(isVisible);

//        newPosition = positionRepository.save(newPosition);

        return newPosition;
    }

    @Override
    public List<Position> saveAllPositions(List<Position> positions) {
        return (List<Position>) positionRepository.saveAll(positions);
    }
}
