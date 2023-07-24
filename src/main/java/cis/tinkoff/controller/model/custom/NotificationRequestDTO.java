package cis.tinkoff.controller.model.custom;

import cis.tinkoff.service.enumerated.SortDirection;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@JsonInclude
public class NotificationRequestDTO {
    private Long notificationId;
    @NotNull
    private Long userId;
    private Boolean isGrouped = false;
    private SortDirection sort = SortDirection.DESC;
    private Integer page = 0;
    private Integer size = 10;
    private Boolean delete = false;
}
