package cis.tinkoff.controller.model;

import cis.tinkoff.model.Notification;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.User;
import cis.tinkoff.model.dictionary.NotificationTypeDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

        return NotificationDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .request(notification.getRequest())
                .user(notification.getUser())
                .createdWhen(notification.getCreatedWhen())
                .build();
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
