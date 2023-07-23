package cis.tinkoff.service;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.controller.model.custom.NotificationCreateDTO;
import cis.tinkoff.controller.model.custom.NotificationRequestDTO;
import cis.tinkoff.model.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getAll();

    Notification createNotification(NotificationCreateDTO notificationCreateDTO);

    List<NotificationDTO> getLatestUserNotificationsByLogin(String login);

    List<NotificationDTO> getUserNotificationsByLogin(
            String login,
            NotificationRequestDTO notificationRequestDTO
    );

    void deleteNotificationById(Long notificationId);
}
