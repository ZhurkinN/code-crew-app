package cis.tinkoff.controller.model.custom;

import cis.tinkoff.service.enumerated.SortDirection;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
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
