package cis.tinkoff.service.impl;

import cis.tinkoff.model.Notification;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.NotificationType;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.NotificationService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final DictionaryService dictionaryService;

    @Override
    public Notification create(NotificationType type, User user) {
        return new Notification()
                .setType(dictionaryService.getNotificationTypeDictionaryById(type))
                .setUser(user);
    }

}
