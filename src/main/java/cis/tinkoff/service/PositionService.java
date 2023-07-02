package cis.tinkoff.service;

import cis.tinkoff.model.Position;

import java.util.List;

public interface PositionService {
    List<Position> searchVacancyList(Long date, String status, String direction, String skills);
}
