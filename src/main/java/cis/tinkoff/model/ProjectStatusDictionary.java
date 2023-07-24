package cis.tinkoff.model;

import cis.tinkoff.model.enumerated.ProjectStatus;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@MappedEntity(value = "dictionary_project_status")
public class ProjectStatusDictionary {

    @Id
    @Enumerated(value = EnumType.STRING)
    private ProjectStatus statusName;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectStatusDictionary that)) return false;
        return getStatusName() == that.getStatusName() && getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusName(), getDescription());
    }
}
