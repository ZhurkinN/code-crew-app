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

//        List<Position> positions = positionPage.getContent();
//        List<Long> projectIds = positions.stream().map(position -> position.getProject().getId()).toList();
//        List<Project> projects = projectRepository.findByIdInList(projectIds);
//
//        positions = positions.stream().filter(position -> {
//            assert position.getSkills() != null;
//
//            return new HashSet<>(position.getSkills()).containsAll(skillList);
//        }).toList();
//
//        //TODO Mapping to DTO
//
//        List<VacancyDTO> vacancies = positions.stream().map(position -> {
//            VacancyDTO vacancyDTO = new VacancyDTO();
//            vacancyDTO.setId(position.getId());
//            vacancyDTO.setDirection(position.getDirection());
//            vacancyDTO.setCreatedWhen(position.getCreatedWhen());
//            vacancyDTO.setSkills(position.getSkills());
//            vacancyDTO.setProjectStatus(
//                    projects.stream().filter(project -> project.getId().equals(position.getProject().getId()))
//                            .findFirst().get().getStatus()
//            );
//            return vacancyDTO;
//        }).toList();

        //--------test------------
//        List<Position> positionPage = positionRepository.searchAllVacancies(direction, status, skillList);
//
//        if (dateSort != null) {
//            positionPage.getSort().order("createdWhen", Sort.Order.Direction.valueOf(dateSort.name()));
//        }
        //-----------------------

        return positionPage.getContent();
    }

    @Override
    public List<Position> getAll() {
        return (List<Position>) positionRepository.findAll();
    }
}
