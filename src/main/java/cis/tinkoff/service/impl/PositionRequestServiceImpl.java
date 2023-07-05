package cis.tinkoff.service.impl;

import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.repository.PositionRequestRepository;
import cis.tinkoff.service.PositionRequestService;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Primary
@Singleton
public class PositionRequestServiceImpl implements PositionRequestService {

    @Inject
    private PositionRequestRepository positionRequestRepository;

    @Override
    public List<PositionRequest> getAll() {
        return positionRequestRepository.list();
    }
}
