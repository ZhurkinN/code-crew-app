package cis.tinkoff.model;

import cis.tinkoff.model.generic.GenericModel;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Relation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true) // вот это можно вынести отовсюду в ломбок.конфиг
@NoArgsConstructor
@MappedEntity(value = "project")
public class Project extends GenericModel {

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "leader_id")
    @Nullable
    private User leader;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "status")
    @Nullable
    private ProjectStatusDictionary status;

    private String title;
    @Nullable
    private String theme;
    @Nullable
    private String description;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "project"
    )
    @Nullable
    private List<Position> positions = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "project"
    )
    @Nullable
    private List<ProjectContact> contacts = new ArrayList<>();

    public Project(Long id,
                   Long createdWhen,
                   Boolean isDeleted,
                   @Nullable User leader,
                   @Nullable ProjectStatusDictionary status,
                   String title,
                   @Nullable String theme,
                   @Nullable String description,
                   @Nullable List<Position> positions,
                   @Nullable List<ProjectContact> contacts) {
        super(id, createdWhen, isDeleted);
        this.leader = leader;
        this.status = status;
        this.title = title;
        this.theme = theme;
        this.description = description;
        this.positions = positions;
        this.contacts = contacts;
    }
}
