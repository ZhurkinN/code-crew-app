package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.model.Notification;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Singleton
public class NotificationMapper {

    public NotificationDTO of(Notification notification) {
        if (Objects.isNull(notification)) {
            return null;
        }

        Long resumeId = null;
        Long vacancyId = null;

        switch (notification.getType().getTypeName()) {
            case INVITE, REQUEST_APPROVED, REQUEST_DECLINED -> resumeId = notification.getRequest().getResume().getId();
            case REQUEST, INVITE_APPROVED, INVITE_DECLINED ->
                    vacancyId = notification.getRequest().getPosition().getId();
        }

        return new NotificationDTO()
                .setId(notification.getId())
                .setType(notification.getType())
                .setCreatedWhen(notification.getCreatedWhen())
                .setProjectId(notification.getRequest().getPosition().getProject().getId())
                .setProjectTitle(notification.getRequest().getPosition().getProject().getTitle())
                .setResumeId(resumeId)
                .setVacancyId(vacancyId);
    }

    public List<NotificationDTO> of(Collection<Notification> notifications) {
        if (Objects.isNull(notifications)) {
            return null;
        }

        return notifications.stream()
                .map(this::of)
                .toList();
    }
}
