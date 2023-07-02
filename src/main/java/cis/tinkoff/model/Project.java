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
@MappedEntity(value = "project")
public class Project extends GenericModel {

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "leader_id")
    @Nullable
    private User user;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @JoinColumn(name = "status")
    private ProjectStatus projectStatus;

    private String title;
    @Nullable
    private String theme;
    @Nullable
    private String description;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "team"
    )
    @Nullable
    private List<Position> positions = new ArrayList<>();

    public Project(Long id,
                   LocalDateTime createdWhen,
                   Boolean isDeleted,
                   @Nullable User user,
                   ProjectStatus projectStatus,
                   String title,
                   @Nullable String theme,
                   @Nullable String description,
                   @Nullable List<Position> positions) {
        super(id, createdWhen, isDeleted);
        this.user = user;
        this.projectStatus = projectStatus;
        this.title = title;
        this.theme = theme;
        this.description = description;
        this.positions = positions;
    }
}
