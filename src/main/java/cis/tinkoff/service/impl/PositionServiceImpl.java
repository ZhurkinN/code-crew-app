package cis.tinkoff.service.impl;

import cis.tinkoff.model.Position;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.service.PositionService;
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

    @Override
    public List<Position> searchVacancyList(Long date, String status, String direction, String skills) {
        List<String> skillList = Arrays.stream(skills.split(" ")).toList();

        Page<Position> positionPage = positionRepository.findAll(Pageable.from(1, 3).order(new Sort.Order("createdWhen")));

        return positionPage.getContent();
    }
}
