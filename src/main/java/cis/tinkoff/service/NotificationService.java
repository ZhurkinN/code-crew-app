package cis.tinkoff.service;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.model.Notification;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.NotificationType;

import java.util.List;

public interface NotificationService {

    Notification create(NotificationType type,
                        User user);

    void createNotification(
            Long targetUserId,
            Long targetRequestId,
            NotificationType notificationType
    );

    NotificationDTO getNotificationById(Long notificationId);

    List<NotificationDTO> getLatestUserNotificationsByLogin(String login);

    void deleteNotificationById(Long notificationId);
}
