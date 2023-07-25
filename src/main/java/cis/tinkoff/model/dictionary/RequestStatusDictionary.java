package cis.tinkoff.model.dictionary;

import cis.tinkoff.model.enumerated.RequestStatus;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedEntity(value = "dictionary_request_status")
public class RequestStatusDictionary {

    @Id
    @Enumerated(value = EnumType.STRING)
    private RequestStatus statusName;
    private String description;

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestStatusDictionary that)) {
            return false;
        }

        return getStatusName() == that.getStatusName()
                && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusName(), getDescription());
    }
}
