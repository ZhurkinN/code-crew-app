package cis.tinkoff.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.jdbc.annotation.JoinColumn;
import io.micronaut.data.jdbc.annotation.JoinTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "resume")
public class Resume {

    @Id
    @GeneratedValue
    private Long id;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "user_id")
    private User user;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @Nullable
    private String description;
    private Boolean isActive;

    @Relation(
            value = Relation.Kind.MANY_TO_MANY,
            cascade = Relation.Cascade.ALL
    )
    @JoinTable(
            name = "resume_skills",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Nullable
    private Set<Skill> skills;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "resume"
    )
    @Nullable
    private Set<TeamRequest> requests = new HashSet<>();

    public Resume(Long id,
                  User user,
                  Direction direction,
                  @Nullable String description,
                  Boolean isActive,
                  @Nullable Set<Skill> skills,
                  @Nullable Set<TeamRequest> requests) {
        this.id = id;
        this.user = user;
        this.direction = direction;
        this.description = description;
        this.isActive = isActive;
        this.skills = skills;
        this.requests = requests;
    }
}