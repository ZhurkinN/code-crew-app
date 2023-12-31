package cis.tinkoff.model.dictionary;

import cis.tinkoff.model.enumerated.Direction;
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
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity(value = "dictionary_direction")
public class DirectionDictionary {

    @Id
    @Enumerated(value = EnumType.STRING)
    private Direction directionName;
    private String description;

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DirectionDictionary that = (DirectionDictionary) o;
        return directionName == that.directionName
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directionName, description);
    }
}
