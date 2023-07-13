package cis.tinkoff.service;

import cis.tinkoff.controller.model.SearchDTO;
import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.enumerated.SortDirection;

import java.util.List;

public interface PositionService {

    SearchDTO searchVacancyList(Integer page,
                                Integer sizeLimit,
                                SortDirection dateSort,
                                String status,
                                String direction,
                                String skills);

    List<Position> getAll();

    VacancyDTO getVacancyById(Long id);

    List<VacancyDTO> getProjectVacancies(Long projectId, Boolean isVisible);

    VacancyDTO createVacancy(String login, Long projectId, VacancyDTO vacancyCreateDTO);

    VacancyDTO updateVacancy(Long id, String login, VacancyDTO updateVacancyDTO);

    VacancyDTO changeVisibility(Long id, String login);

    void deleteVacancy(Long id, String login);
}
