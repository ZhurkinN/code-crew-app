package cis.tinkoff.model;

import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.support.helper.TimestampAttributeConverter;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
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
@MappedEntity(value = "position")
public class Position extends GenericModel {

    @Nullable
    private String description;
    @Nullable
    @TypeDef(type = DataType.STRING_ARRAY)
    private List<String> skills;
    private Boolean isVisible;

    @Nullable
    @TypeDef(type = DataType.TIMESTAMP, converter = TimestampAttributeConverter.class)
    private Long joinDate;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "user_id")
    @Nullable
    private User user;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "project_id")
    @Nullable
    private Project project;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "direction")
    @Nullable
    private DirectionDictionary direction;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "position"
    )
    @Nullable
    private List<PositionRequest> requests = new ArrayList<>();

    public Position(Long id,
                    Long createdWhen,
                    Boolean isDeleted,
                    @Nullable String description,
                    @Nullable List<String> skills,
                    @Nullable User user,
                    @Nullable Project project,
                    Boolean isVisible,
                    @Nullable DirectionDictionary direction,
                    @Nullable List<PositionRequest> requests) {
        super(id, createdWhen, isDeleted);
        this.description = description;
        this.skills = skills;
        this.user = user;
        this.project = project;
        this.isVisible = isVisible;
        this.direction = direction;
        this.requests = requests;
    }
}
