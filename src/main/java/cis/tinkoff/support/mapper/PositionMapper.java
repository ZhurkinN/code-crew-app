package cis.tinkoff.support.mapper;


import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.User;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Singleton
@RequiredArgsConstructor
public class PositionMapper {
    private final ProjectMapper projectMapper;
    public VacancyDTO toVacancyDTO(Position position) {
        if (position == null) {
            return null;
        }

        return new VacancyDTO()
                .setId(position.getId())
                .setDirection(position.getDirection())
                .setDescription(position.getDescription())
                .setSkills(position.getSkills())
                .setCreatedWhen(position.getCreatedWhen())
                .setIsVisible(position.getIsVisible())
                .setProject(projectMapper.toProjectDTO(position.getProject(), ""));
    }

    public List<VacancyDTO> toVacancyDTO(Collection<Position> positions) {
        if (positions == null) {
            return null;
        }

        return positions.stream()
                .map(this::toVacancyDTO)
                .toList();
    }

    public ProjectMemberDTO toProjectMemberDTO(User user, Position position, Long leaderId) {
        if (user == null || position == null) {
            return null;
        }

        return new ProjectMemberDTO()
                .setUserId(user.getId())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setPictureLink(null) //TODO insert picture link
                .setDirection(position.getDirection())
                .setJoinDate(position.getJoinDate())
                .setIsLead(Objects.equals(user.getId(), leaderId));
    }

    public List<ProjectMemberDTO> toProjectMemberDTO(List<User> users, List<Position> positions, Long leaderId) {
        if (users == null || positions == null) {
            return null;
        }

        return positions.stream()
                .filter(position -> position.getUser() != null)
                .map(position -> {
                    User member = users.stream().filter(user -> user.getId() == position.getUser().getId())
                            .findFirst().orElse(null);
                    ProjectMemberDTO projectMemberDTO = toProjectMemberDTO(member, position, leaderId);

                    return projectMemberDTO;
                })
                .toList();
    }
}
