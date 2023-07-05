package cis.tinkoff.model;

import cis.tinkoff.model.enumerated.RequestStatus;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@MappedEntity(value = "dictionary_request_status")
public class RequestStatusDictionary {

    @Id
    @Enumerated(value = EnumType.STRING)
    private RequestStatus statusName;
    private String description;
}
