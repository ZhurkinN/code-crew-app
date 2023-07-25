package cis.tinkoff.controller.model;

import cis.tinkoff.model.Notification;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.User;
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
    private PositionRequest request;
    private User user;
    private Long createdWhen;

    public static NotificationDTO of(Notification notification) {

        if (Objects.isNull(notification)) {
            return null;
        }

        return new NotificationDTO()
                .setId(notification.getId())
                .setType(notification.getType())
                .setRequest(notification.getRequest())
                .setUser(notification.getUser())
                .setCreatedWhen(notification.getCreatedWhen());
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