package cis.tinkoff.service;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.controller.model.custom.NotificationRequestDTO;
import cis.tinkoff.model.Notification;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.NotificationType;

import java.util.List;

public interface NotificationService {

    Notification create(NotificationType type,
                        User user);

    Notification createNotification(
            Long targetUserId,
            Long targetRequestId,
            NotificationType notificationType
    );

    List<NotificationDTO> getLatestUserNotificationsByLogin(String login);

    List<NotificationDTO> getUserNotificationsByLogin(
            String login,
            NotificationRequestDTO notificationRequestDTO
    );

    void deleteNotificationById(Long notificationId);
}
