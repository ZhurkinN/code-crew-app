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
}
