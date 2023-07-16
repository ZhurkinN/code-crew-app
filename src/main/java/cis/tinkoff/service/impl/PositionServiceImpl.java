package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import cis.tinkoff.model.enumerated.SortDirection;
import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.service.PositionService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.mapper.PositionMapper;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Primary
@Singleton
public class PositionServiceImpl implements PositionService {

    @Inject
    private PositionRepository positionRepository;
    @Inject
    private ProjectRepository projectRepository;
    @Inject
    private DirectionRepository directionRepository;
    @Inject
    private PositionMapper positionMapper;

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
        List<Position> positions = positionRepository.findByIdInList(List.of(id), Sort.UNSORTED);

        if (positions.size() == 0) {
            throw new RecordNotFoundException("Vacancy with id=" + id + " not found");
        }

        Position position = positions.get(0);

        if (!position.getIsVisible() || position.getUser() != null) {
            throw new InaccessibleActionException("Vacancy with id=" + id + " is inaccessible");
        }

        return VacancyDTO.toVacancyDTO(position);
    }

    @Override
    public List<VacancyDTO> getProjectVacancies(Long projectId, Boolean isVisible) {
        List<Position> positions = positionRepository.findByProjectId(projectId, isVisible);

        return VacancyDTO.toVacancyDTO(positions);
    }

    @Override
    public VacancyDTO createVacancy(String login, Long projectId, VacancyDTO vacancyCreateDTO) {
        DirectionDictionary directionDictionary = directionRepository
                .findById(vacancyCreateDTO.getDirection().getDirectionName())
                .orElseThrow(() -> new RecordNotFoundException("Direction with id=" +
                        vacancyCreateDTO.getDirection().getDirectionName()
                        + " not found"));

        List<Project> projects = projectRepository.findByIdInList(List.of(projectId));

        if (projects.size() == 0) {
            throw new RecordNotFoundException("Project with id=" + projectId + " not found");
        }

        Project project = projects.get(0);

        if (!project.getLeader().getEmail().equals(login)) {
            throw new InaccessibleActionException("Actions whit project witch id=" + projectId + " is inaccessible");
        }

        Position newPosition = new Position();

        VacancyDTO.updateEntityByDTO(newPosition, vacancyCreateDTO);
        newPosition.setCreatedWhen(System.currentTimeMillis());
        newPosition.setIsDeleted(false);
        newPosition.setIsVisible(true);
        newPosition.setProject(project);

        newPosition = positionRepository.save(newPosition);

        return VacancyDTO.toVacancyDTO(newPosition);
    }

    @Override
    public VacancyDTO updateVacancy(Long id, String login, VacancyDTO updateVacancyDTO) {
        DirectionDictionary directionDictionary = directionRepository
                .findById(updateVacancyDTO.getDirection().getDirectionName())
                .orElseThrow(() -> new RecordNotFoundException("Direction with id=" +
                        updateVacancyDTO.getDirection().getDirectionName()
                        + " not found"));

        List<Position> positions = positionRepository.findByIdInList(List.of(id), Sort.UNSORTED);

        if (positions.size() == 0) {
            throw new RecordNotFoundException("Position with id=" + id + " not found");
        }

        Position updatedPosition = positions.get(0);

        List<Project> projects = projectRepository.findByIdInList(List.of(updatedPosition.getProject().getId()));

        if (projects.size() == 0) {
            throw new RecordNotFoundException("Project with id=" +
                    updatedPosition.getProject().getId()
                    + " not found");
        }

        if (!projects.get(0).getLeader().getEmail().equals(login)) {
            throw new InaccessibleActionException("Actions whit project witch id=" +
                    projects.get(0).getId()
                    + " is inaccessible");
        }

        VacancyDTO.updateEntityByDTO(updatedPosition, updateVacancyDTO);

        updatedPosition = positionRepository.update(updatedPosition);

        return VacancyDTO.toVacancyDTO(updatedPosition);
    }

    @Override
    public VacancyDTO changeVisibility(Long id, String login) {
        List<Position> positions = positionRepository.findByIdInList(List.of(id), Sort.UNSORTED);

        if (positions.size() == 0) {
            throw new RecordNotFoundException("Position with id=" + id + " not found");
        }

        Position updatedPosition = positions.get(0);

        List<Project> projects = projectRepository.findByIdInList(List.of(updatedPosition.getProject().getId()));

        if (projects.size() == 0) {
            throw new RecordNotFoundException("Project with id=" +
                    updatedPosition.getProject().getId()
                    + " not found");
        }

        if (!projects.get(0).getLeader().getEmail().equals(login)) {
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
        List<Position> positions = positionRepository.findByIdInList(List.of(id), Sort.UNSORTED);

        if (positions.size() == 0) {
            throw new RecordNotFoundException("Position with id=" + id + " not found");
        }

        Position updatedPosition = positions.get(0);

        List<Project> projects = projectRepository.findByIdInList(List.of(updatedPosition.getProject().getId()));

        if (projects.size() == 0) {
            throw new RecordNotFoundException("Project with id=" +
                    updatedPosition.getProject().getId()
                    + " not found");
        }

        if (!projects.get(0).getLeader().getEmail().equals(login)) {
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

    public boolean isUserProjectMember(String login, Long projectId) {
        List<Position> members = positionRepository.findByProjectIdAndUserEmail(projectId, login);

        if (members.size() == 0) {
            throw new RecordNotFoundException("There is no user with login = " + login +
                    " in project with id=" + projectId);
        }

        return true;
    }
}
