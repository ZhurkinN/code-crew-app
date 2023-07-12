package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.PositionRequestDTO;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;

import java.util.Objects;

@Singleton
public class PositionRequestMapper extends GenericMapper<PositionRequest, PositionRequestDTO> {

    @Override
    public PositionRequestDTO toDto(PositionRequest positionRequest) {

        if (Objects.isNull(positionRequest)) {
            return null;
        }

        PositionRequestDTO dto = new PositionRequestDTO()
                .setCoverLetter(positionRequest.getCoverLetter())
                .setIsInvite(positionRequest.getIsInvite())
                .setStatus(positionRequest.getStatus())
                .setResume(positionRequest.getResume())
                .setPosition(positionRequest.getPosition());
        setGenericFields(positionRequest, dto);
        return dto;
    }
}
