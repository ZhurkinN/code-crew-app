package cis.tinkoff.service.impl;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.enumerated.SortDirection;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.service.PositionService;
import cis.tinkoff.support.helper.Specifications;
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
    private ProjectRepository projectRepository;

    @Override
    public List<Position> searchVacancyList(Integer page,
                                            Integer sizeLimit,
                                            SortDirection dateSort,
                                            String status,
                                            String direction,
                                            String skills) {
        List<String> skillList = Arrays.stream(skills.split(" ")).toList();
        Page<Position> positionPage = positionRepository.findAll(Specifications.searchVacancies(status, direction, skills),
                Pageable.from(page, sizeLimit));

        if (dateSort != null) {
            positionPage.getSort().order("createdWhen", Sort.Order.Direction.valueOf(dateSort.name()));
        }

        return positionPage.getContent();
    }

    @Override
    public List<Position> getAll() {
        return (List<Position>) positionRepository.findAll();
    }
}
