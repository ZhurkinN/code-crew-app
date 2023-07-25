package cis.tinkoff.controller.model;

import cis.tinkoff.model.dictionary.NotificationTypeDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
