package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.controller.model.custom.NotificationCreateDTO;
import cis.tinkoff.controller.model.custom.NotificationRequestDTO;
import cis.tinkoff.model.Notification;
import cis.tinkoff.repository.NotificationRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.NotificationService;
import cis.tinkoff.service.UserService;
import cis.tinkoff.service.enumerated.SortDirection;
import cis.tinkoff.service.event.*;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
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

    @Override
    public List<Notification> getAll() {
        return (List<Notification>) notificationRepository.findAll();
    }

    @Override
    public Notification createNotification(NotificationCreateDTO notificationCreateDTO) {
        Notification newNotification = new Notification()
                .setUser(userService.getById(notificationCreateDTO.getUserId()))
                .setCreatedWhen(notificationCreateDTO.getCreatedWhen())
                .setType(dictionaryService.getNotificationTypeDictionaryById(notificationCreateDTO.getType()))
                .setRequest(notificationCreateDTO.getRequest());

        newNotification = notificationRepository.save(newNotification);

        NotificationEvent event;

        switch (notificationCreateDTO.getType()) {
            case INVITE -> event = new InviteCreatedEvent(newNotification);
            case INVITE_APPROVED -> event = new InviteApprovedEvent(newNotification);
            case INVITE_DECLINED -> event = new InviteDeclineEvent(newNotification);
            case REQUEST_APPROVED -> event = new RequestApprovedEvent(newNotification);
            case REQUEST_DECLINED -> event = new RequestDeclineEvent(newNotification);
            default -> event = new RequestCreatedEvent(newNotification);
        }

        eventPublisher.publishEvent(event);

        return newNotification;
    }

    @Override
    public List<NotificationDTO> getLatestUserNotificationsByLogin(String login) {
        Pageable pageable = createPageable(0, 10, SortDirection.DESC);

        Long userId = userService.getByEmail(login).getId();

        Page<Notification> notifications = notificationRepository.findByUserId(userId, pageable);

        return NotificationDTO.of(notifications.getContent());
    }

    @Override
    public List<NotificationDTO> getUserNotificationsByLogin(
            String login,
            NotificationRequestDTO notificationRequestDTO
    ) {
        Pageable pageable = createPageable(
                notificationRequestDTO.getPage(),
                notificationRequestDTO.getSize(),
                notificationRequestDTO.getSort()
        );

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
