package cis.tinkoff.service;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;

import java.util.List;

public interface PositionSupportService {

    boolean isUserProjectMember(String login, Long projectId);

    Position findPositionByIdOrElseThrow(Long id);

    List<Position> findPositionsByUserAndProjectOrElseThrow(Long userId, Long projectId);

    void deleteUserFromPositionsByUserIdAndProjectId(Long userId, Long projectId);

    void softDeletePositionsByProjectId(Long projectId);

    Position createPosition(User user, Direction direction, String description, List<String> skills, Long joinDate, Boolean isVisible);
}
