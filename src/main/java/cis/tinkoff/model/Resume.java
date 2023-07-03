package cis.tinkoff.model;

import cis.tinkoff.model.generic.GenericModel;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.jdbc.annotation.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "resume")
public class Resume extends GenericModel {

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "user_id")
    @Nullable
    private User user;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @JoinColumn(name = "direction")
    private Direction direction;

    @Nullable
    private String description;
    private Boolean isActive;
    @Nullable
    private List<String> skills;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "resume"
    )
    @Nullable
    private List<PositionRequest> requests = new ArrayList<>();

    public Resume(Long id,
                  LocalDateTime createdWhen,
                  Boolean isDeleted,
                  @Nullable User user,
                  Direction direction,
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
