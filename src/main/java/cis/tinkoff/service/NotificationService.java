package cis.tinkoff.service;

import cis.tinkoff.model.Notification;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.NotificationType;

public interface NotificationService {

    Notification create(NotificationType type,
                        User user);
}
