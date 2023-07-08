package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import cis.tinkoff.model.enumerated.SortDirection;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.service.PositionService;
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
    public List<VacancyDTO> searchVacancyList(Integer page,
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

        Page<Position> positionPage = positionRepository.searchAllVacancies(
                vacancyDirection,
                projectStatus,
                skillList, Pageable.from(page, sizeLimit));

        if (dateSort != null) {
            positionPage.getSort().order("createdWhen", Sort.Order.Direction.valueOf(dateSort.name()));
        }

        List<Position> positions = (List<Position>) positionRepository.findByIdInList(
                positionPage.getContent().stream().map(position -> position.getId()).toList()
        );

        return positionMapper.toVacancyDTO(positions);
    }

    @Override
    public List<Position> getAll() {
        return (List<Position>) positionRepository.findAll();
    }
}
