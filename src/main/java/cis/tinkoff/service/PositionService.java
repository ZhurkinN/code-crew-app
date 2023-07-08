package cis.tinkoff.service;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.enumerated.SortDirection;

import java.util.List;

public interface PositionService {

    List<VacancyDTO> searchVacancyList(Integer page,
                                       Integer sizeLimit,
                                       SortDirection dateSort,
                                       String status,
                                       String direction,
                                       String skills);

    List<Position> getAll();
}
