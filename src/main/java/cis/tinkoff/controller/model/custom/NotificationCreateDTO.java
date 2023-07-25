package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.enumerated.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationCreateDTO {

    private NotificationType type;
    private Long requestId;
    private Long userId;
    private Long createdWhen;
}
