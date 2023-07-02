package cis.tinkoff.service.impl;

import cis.tinkoff.helper.Specifications;
import cis.tinkoff.model.Position;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.service.PositionService;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
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

        Page<Position> positionPage = positionRepository.findAll(Specifications.searchVacancies(date, status, direction, skills), new Pageable() {
            @Override
            public int getNumber() {
                return 1;
            }

            @Override
            public int getSize() {
                return 5;
            }
        });

        return positionPage.getContent();
    }
}
