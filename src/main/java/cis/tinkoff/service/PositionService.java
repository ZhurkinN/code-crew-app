package cis.tinkoff.service;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.controller.model.custom.VacancyCreateDTO;
import cis.tinkoff.service.enumerated.SortDirection;

import java.util.List;

public interface PositionService {

    SearchDTO searchVacancyList(Integer page,
                                Integer sizeLimit,
                                SortDirection dateSort,
                                String status,
                                String direction,
                                String skills);

    VacancyDTO getVacancyById(Long id,
                              String userEmail);

    List<VacancyDTO> getProjectVacancies(String login, Long projectId, Boolean isVisible);

    VacancyDTO createVacancy(String login, Long projectId, VacancyCreateDTO vacancyCreateDTO);

    VacancyDTO updateVacancy(Long id, String login, VacancyCreateDTO updateVacancyDTO);

    VacancyDTO changeVisibility(Long id, String login);

    void deleteVacancy(Long id, String login);

    List<ProjectMemberDTO> getProjectMembers(String login, Long projectId);
}
