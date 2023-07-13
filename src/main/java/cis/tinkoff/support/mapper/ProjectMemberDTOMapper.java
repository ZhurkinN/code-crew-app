package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.User;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class ProjectMemberDTOMapper {
    public ProjectMemberDTO toProjectMemberDTO(User user, Position position) {
        if (user == null || position == null) {
            return null;
        }

        ProjectMemberDTO projectMemberDTO = ProjectMemberDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .pictureLink(null) //TODO insert picture link
                .direction(position.getDirection())
                .joinDate(null)//TODO insert join date
                .build();

        return projectMemberDTO;
    }

    public List<ProjectMemberDTO> toProjectMemberDTO(List<User> users, List<Position> positions) {
        if (users == null || positions == null) {
            return null;
        }

        List<ProjectMemberDTO> projectMemberDTOList = positions.stream()
                .map(position -> {
                    User member = users.stream().filter(user -> user.getId() == position.getUser().getId())
                            .findFirst().orElse(null);
                    ProjectMemberDTO projectMemberDTO = toProjectMemberDTO(member, position);

                    return projectMemberDTO;
                })
                .toList();

        return projectMemberDTOList;
    }
}
