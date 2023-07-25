package cis.tinkoff.service.impl;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.User;
import cis.tinkoff.model.dictionary.DirectionDictionary;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.PositionSupportService;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.POSITION_NOT_FOUND;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.POSITION_NOT_FOUND_BY_USER;

@Singleton
@RequiredArgsConstructor
public class PositionSupportServiceImpl implements PositionSupportService {

    private final PositionRepository positionRepository;
    private final DictionaryService dictionaryService;

    @Override
    public boolean isUserProjectMember(String login, Long projectId) {
        List<Position> members = positionRepository.findByProjectIdAndUserEmail(projectId, login);

        return members.size() != 0;
    }

    @Override
    public Position findPositionByIdOrElseThrow(Long id) {

        return positionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND, id));
    }

    @Override
    public List<Position> findPositionsByUserAndProjectOrElseThrow(Long userId, Long projectId) {
        List<Position> positions = positionRepository.findByUserIdAndProjectId(userId, projectId);

        if (positions.size() == 0) {
            throw new RecordNotFoundException(POSITION_NOT_FOUND_BY_USER, userId);
        }

        return positions;
    }

    @Override
    public void deleteUserFromPositionsByUserIdAndProjectId(Long userId, Long projectId) {
        positionRepository.deleteUserFromPositionsByUserIdAndProjectId(userId, projectId);
    }

    @Override
    public void softDeletePositionsByProjectId(Long projectId) {
        positionRepository.deletePositionsByProjectId(projectId);
    }

    @Override
    public Position createPosition(
            @NonNull User user,
            @NonNull Direction direction,
            String description,
            List<String> skills,
            Long joinDate,
            Boolean isVisible
    ) {
        DirectionDictionary directionDictionary = dictionaryService
                .getDirectionDictionaryById(direction);
        Position newPosition = new Position();

        newPosition.setUser(user);
        newPosition.setDirection(directionDictionary);
        newPosition.setDescription(description);
        newPosition.setJoinDate(joinDate);
        newPosition.setIsVisible(isVisible);

        return newPosition;
    }
}
