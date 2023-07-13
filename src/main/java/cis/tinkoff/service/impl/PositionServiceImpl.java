package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.SearchDTO;
import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import cis.tinkoff.model.enumerated.SortDirection;
import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.repository.PositionRepository;
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

@Primary
@Singleton
public class PositionServiceImpl implements PositionService {

    @Inject
    private PositionRepository positionRepository;
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
}
