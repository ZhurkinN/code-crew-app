package cis.tinkoff.service;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.controller.model.custom.VacancyCreateDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.SortDirection;

import java.util.List;

public interface PositionService {

    SearchDTO searchVacancyList(Integer page,
                                Integer sizeLimit,
                                SortDirection dateSort,
                                String status,
                                String direction,
                                String skills);

    VacancyDTO getVacancyById(Long id);

    List<VacancyDTO> getProjectVacancies(Long projectId, Boolean isVisible);

    VacancyDTO createVacancy(String login, Long projectId, VacancyCreateDTO vacancyCreateDTO);

    VacancyDTO updateVacancy(Long id, String login, VacancyCreateDTO updateVacancyDTO);

    VacancyDTO changeVisibility(Long id, String login);

    void deleteVacancy(Long id, String login);

    List<ProjectMemberDTO> getProjectMembers(String name, Long projectId);

    boolean isUserProjectMember(String login, Long projectId);

    List<Position> findPositionsByIdsOrElseThrow(List<Long> ids);

    List<Position> findPositionsByUserAndProjectAndDirectionOrElseThrow(Long userId, Long projectId, Direction direction);

    Position createPosition(User user, Project project, Direction direction, String description, List<String> skills, Long joinDate, Boolean isVisible);

    List<Position> saveAllPositions(List<Position> positions);
}
