package cis.tinkoff.service;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.enumerated.SortDirection;

import java.util.List;

public interface PositionService {

    List<Position> searchVacancyList(Integer page,
                                     Integer sizeLimit,
                                     SortDirection dateSort,
                                     String status,
                                     String direction,
                                     String skills);

    List<Position> getAll();
}
