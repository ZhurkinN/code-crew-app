package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.ArrayList;
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

    public static ProjectMemberDTO toProjectMemberDTO(User user, Position position, Long leaderId) {
        if (user == null || position == null) {
            return null;
        }

        ProjectMemberDTO projectMemberDTO = ProjectMemberDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .pictureLink(null) //TODO insert picture link
                .direction(position.getDirection())
                .joinDate(position.getJoinDate())
                .isLead(user.getId() == leaderId)
                .build();

        return projectMemberDTO;
    }

    public static List<ProjectMemberDTO> toProjectMemberDTO(List<User> users, List<Position> positions, Long leaderId) {
        if (users == null || positions == null) {
            return null;
        }

        List<ProjectMemberDTO> projectMemberDTOList = positions.stream()
                .filter(position -> position.getUser() != null)
                .map(position -> {
                    User member = users.stream().filter(user -> user.getId() == position.getUser().getId())
                            .findFirst().orElse(null);
                    ProjectMemberDTO projectMemberDTO = toProjectMemberDTO(member, position, leaderId);

                    return projectMemberDTO;
                })
                .toList();

        return projectMemberDTOList;
    }
}
