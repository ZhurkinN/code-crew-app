package cis.tinkoff.controller.model;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude
public class ProjectMemberDTO {
    private Long userId;
    private String name;
    private String surname;
    private String pictureLink;
    private DirectionDictionary direction;
    private Long joinDate;
    private Boolean isLead;

    public static ProjectMemberDTO toProjectMemberDTO(User user, Position position) {
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

    public static List<ProjectMemberDTO> toProjectMemberDTO(List<User> users, List<Position> positions) {
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
