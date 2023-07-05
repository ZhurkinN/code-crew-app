package cis.tinkoff.model;

import cis.tinkoff.model.enumerated.ProjectStatus;
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
@MappedEntity(value = "dictionary_project_status")
public class ProjectStatusDictionary {

    @Id
    @Enumerated(value = EnumType.STRING)
    private ProjectStatus statusName;
    private String description;
}
