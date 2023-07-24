package cis.tinkoff.model;

import cis.tinkoff.model.enumerated.NotificationType;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true) // вот это можно вынести отовсюду в ломбок.конфиг
@NoArgsConstructor
@MappedEntity(value = "dictionary_notification_type")
public class NotificationTypeDictionary {

    @Id
    @Enumerated(value = EnumType.STRING)
    private NotificationType typeName;
    private String description;
}
