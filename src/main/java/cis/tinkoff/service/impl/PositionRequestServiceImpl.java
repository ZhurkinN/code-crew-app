package cis.tinkoff.service.impl;

import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.repository.PositionRequestRepository;
import cis.tinkoff.service.PositionRequestService;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Primary
@Singleton
@RequiredArgsConstructor
public class PositionRequestServiceImpl implements PositionRequestService {

    private final PositionRequestRepository positionRequestRepository;

    @Override
    public List<PositionRequest> getAll() {
        return positionRequestRepository.list();
    }
}
