package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.model.Notification;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.NotificationType;
import cis.tinkoff.repository.NotificationRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.NotificationService;
import cis.tinkoff.service.PositionRequestService;
import cis.tinkoff.service.UserService;
import cis.tinkoff.service.enumerated.SortDirection;
import cis.tinkoff.service.event.NotificationEvent;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final ApplicationEventPublisher<NotificationEvent> eventPublisher;

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final DictionaryService dictionaryService;
    private final Provider<PositionRequestService> positionRequestServiceProvider;

    @Override
    public Notification create(NotificationType type, User user) {
        return new Notification()
                .setType(dictionaryService.getNotificationTypeDictionaryById(type))
                .setUser(user);
    }

    @Override
    public Notification createNotification(
            Long targetUserId,
            Long targetRequestId,
            NotificationType notificationType
    ) {
        Notification newNotification = new Notification()
                .setUser(userService.getByIdWithoutProjects(targetUserId))
                .setType(dictionaryService.getNotificationTypeDictionaryById(notificationType))
                .setRequest(
                        positionRequestServiceProvider.get()
                                .findPositionRequestById(targetRequestId)
                );

        newNotification = notificationRepository.save(newNotification);

        eventPublisher.publishEvent(new NotificationEvent(newNotification));

        return newNotification;
    }

    @Override
    public NotificationDTO getNotificationById(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RecordNotFoundException(
                        ErrorDisplayMessageKeeper.NOTIFICATION_NOT_FOUND,
                        notificationId
                ));

        return NotificationDTO.of(notification);
    }

    @Override
    public List<NotificationDTO> getLatestUserNotificationsByLogin(String login) {
        Pageable pageable = createPageable(0, 10, SortDirection.DESC);

        Long userId = userService.getByEmail(login).getId();

        Page<Notification> notifications = notificationRepository.findByUserId(userId, pageable);

        return NotificationDTO.of(notifications.getContent());
    }

    @Override
    public void deleteNotificationById(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    private Pageable createPageable(Integer pageNumber, Integer sizeLimit, SortDirection direction) {

        Sort.Order.Direction sortDirection = Sort.Order.Direction.valueOf(direction.name());
        Sort.Order sortOrder = new Sort.Order("createdWhen", sortDirection, false);

        return Pageable.from(pageNumber, sizeLimit, Sort.of(sortOrder));
    }

}
