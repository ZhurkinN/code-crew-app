package cis.tinkoff.model;

import cis.tinkoff.model.dictionary.DirectionDictionary;
import cis.tinkoff.model.generic.GenericModel;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@MappedEntity(value = "resume")
public class Resume extends GenericModel {

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
    @MappedProperty(value = "direction")
    @Nullable
    private DirectionDictionary direction;

    @Nullable
    private String description;
    private Boolean isActive = true;

    @Nullable
    @TypeDef(type = DataType.STRING_ARRAY)
    private List<String> skills = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "resume"
    )
    @Nullable
    private List<PositionRequest> requests = new ArrayList<>();

    public Resume(Long id,
                  Long createdWhen,
                  Boolean isDeleted,
                  @Nullable User user,
                  @Nullable DirectionDictionary direction,
                  @Nullable String description,
                  Boolean isActive,
                  @Nullable List<String> skills,
                  @Nullable List<PositionRequest> requests) {
        super(id, createdWhen, isDeleted);
        this.user = user;
        this.direction = direction;
        this.description = description;
        this.isActive = isActive;
        this.skills = skills;
        this.requests = requests;
    }
}
