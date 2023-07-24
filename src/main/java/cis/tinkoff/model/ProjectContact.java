package cis.tinkoff.model;

import io.micronaut.data.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true) // вот это можно вынести отовсюду в ломбок.конфиг
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
