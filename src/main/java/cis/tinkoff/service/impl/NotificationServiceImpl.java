package cis.tinkoff.service.impl;

import cis.tinkoff.model.Notification;
import cis.tinkoff.repository.NotificationRepository;
import cis.tinkoff.service.NotificationService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAll() {
        return (List<Notification>) notificationRepository.findAll();
    }
}
