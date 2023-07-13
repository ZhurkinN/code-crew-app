package cis.tinkoff.model;

import io.micronaut.data.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "project_information")
public class ProjectContact {

    @Id
    @GeneratedValue
    private Long id;
    private String link;
    private String description;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "project_id")
    private Project project;
}
