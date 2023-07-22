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
import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.PositionService;
import cis.tinkoff.service.PositionSupportService;
import cis.tinkoff.service.ProjectSupportService;
import cis.tinkoff.service.enumerated.SortDirection;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.INACCESSIBLE_PROJECT_ACTION;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.POSITION_NOT_FOUND;

@Singleton
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final DictionaryService dictionaryService;
    private final PositionSupportService positionSupportService;
    private final ProjectSupportService projectSupportService;

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
    public VacancyDTO getVacancyById(Long positionId,
                                     String userEmail) {

        Position vacancy = positionRepository.findByIdAndIsDeletedFalse(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND, positionId));

        if ((!vacancy.getIsVisible() || Objects.nonNull(vacancy.getUser()))
                && !projectSupportService.isUserProjectLeader(userEmail, vacancy.getProject().getId())) {
            throw new RecordNotFoundException(POSITION_NOT_FOUND, positionId);
        }

        return VacancyDTO.toVacancyDTO(vacancy);
    }

    @Override
    public List<VacancyDTO> getProjectVacancies(Long projectId, Boolean isVisible) {
        List<Position> positions = positionRepository.findByProjectIdAndIsDeletedFalse(projectId, isVisible);

        return VacancyDTO.toVacancyDTO(positions);
    }

    @Override
    public VacancyDTO createVacancy(String email,
                                    Long projectId,
                                    VacancyCreateDTO vacancyCreateDTO) {
        DirectionDictionary directionDictionary = dictionaryService
                .getDirectionDictionaryById(vacancyCreateDTO.getDirection());

        if (!projectSupportService.isUserProjectLeader(email, projectId)) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    email,
                    projectId
            );
        }

        Project project = projectSupportService.getProjectByIdOrElseThrow(projectId);

        Position newPosition = new Position()
                .setDirection(directionDictionary)
                .setDescription(vacancyCreateDTO.getDescription())
                .setSkills(vacancyCreateDTO.getSkills())
                .setUser(null)
                .setIsVisible(true)
                .setProject(project);

        newPosition = positionRepository.save(newPosition);

        return VacancyDTO.toVacancyDTO(newPosition);
    }

    @Override
    public VacancyDTO updateVacancy(Long id,
                                    String email,
                                    VacancyCreateDTO updateVacancyDTO) {
        DirectionDictionary directionDictionary = dictionaryService
                .getDirectionDictionaryById(updateVacancyDTO.getDirection());

        Position updatedPosition = positionSupportService.findPositionByIdOrElseThrow(id);

        Long projectId = updatedPosition.getProject().getId();

        if (!projectSupportService.isUserProjectLeader(email, projectId)) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    email,
                    projectId
            );
        }

        updatedPosition.setDirection(directionDictionary)
                .setDescription(updateVacancyDTO.getDescription())
                .setSkills(updateVacancyDTO.getSkills());

        updatedPosition = positionRepository.update(updatedPosition);

        return VacancyDTO.toVacancyDTO(updatedPosition);
    }

    @Override
    public VacancyDTO changeVisibility(Long id,
                                       String email) {
        Position updatedPosition = positionSupportService.findPositionByIdOrElseThrow(id);

        Long projectId = updatedPosition.getProject().getId();

        if (!projectSupportService.isUserProjectLeader(email, projectId)) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    email,
                    projectId
            );
        }

        updatedPosition.setIsVisible(!updatedPosition.getIsVisible());

        updatedPosition = positionRepository.update(updatedPosition);

        return VacancyDTO.toVacancyDTO(updatedPosition);
    }

    @Override
    public void deleteVacancy(Long id,
                              String email) {
        Position updatedPosition = positionSupportService.findPositionByIdOrElseThrow(id);

        Long projectId = updatedPosition.getProject().getId();

        if (!projectSupportService.isUserProjectLeader(email, projectId)) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_PROJECT_ACTION,
                    email,
                    projectId
            );
        }

        updatedPosition.setIsDeleted(true);
        updatedPosition.setIsVisible(false);
        positionRepository.update(updatedPosition);
    }

    @Override
    public List<ProjectMemberDTO> getProjectMembers(String login,
                                                    Long projectId) {
        List<Position> positions = positionRepository.retrieveByProjectId(projectId);
        List<User> members = positions.stream()
                .map(Position::getUser)
                .filter(Objects::nonNull)
                .toList();
        User leader = positions.get(0).getProject().getLeader();

        return ProjectMemberDTO.toProjectMemberDTO(members, positions, leader.getId());
    }
}
