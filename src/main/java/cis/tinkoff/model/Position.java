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
@MappedEntity(value = "positions")
public class Position extends GenericModel {

    @Nullable
    private String description;
    @Nullable
    private String[] skills;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "user_id")
    @Nullable
    private User user;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "project_id")
    private Project project;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @JoinColumn(name = "direction")
    private Direction direction;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "position"
    )
    @Nullable
    private List<PositionRequest> requests = new ArrayList<>();

    public Position(Long id,
                    LocalDateTime createdWhen,
                    Boolean isDeleted,
                    @Nullable String description,
                    @Nullable String[] skills,
                    @Nullable User user,
                    Project project,
                    Direction direction,
                    @Nullable List<PositionRequest> requests) {
        super(id, createdWhen, isDeleted);
        this.description = description;
        this.skills = skills;
        this.user = user;
        this.project = project;
        this.direction = direction;
        this.requests = requests;
    }
}
