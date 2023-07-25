package cis.tinkoff.controller.model;

import cis.tinkoff.model.Notification;
import cis.tinkoff.model.dictionary.NotificationTypeDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude
public class NotificationDTO {

    private Long id;
    private NotificationTypeDictionary type;
    private String projectTitle;
    private Long resumeId;
    private Long vacancyId;
    private Long projectId;
    private Long createdWhen;

    public static NotificationDTO of(Notification notification) {
        if (Objects.isNull(notification)) {
            return null;
        }

        Long resumeId = null;
        Long vacancyId = null;

        switch (notification.getType().getTypeName()) {
            case INVITE, REQUEST_APPROVED, REQUEST_DECLINED -> resumeId = notification.getRequest().getResume().getId();
            case REQUEST, INVITE_APPROVED, INVITE_DECLINED -> vacancyId = notification.getRequest().getPosition().getId();
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

    public static List<NotificationDTO> of(Collection<Notification> notifications) {
        if (Objects.isNull(notifications)) {
            return null;
        }

        return notifications.stream()
                .map(NotificationDTO::of)
                .toList();
    }
}
