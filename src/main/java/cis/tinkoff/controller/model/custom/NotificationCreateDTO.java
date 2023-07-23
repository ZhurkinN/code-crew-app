package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.enumerated.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class NotificationCreateDTO {
    private NotificationType type;
    private PositionRequest request;
    private Long userId;
    private Long createdWhen;
}
