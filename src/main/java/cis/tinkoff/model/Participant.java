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
@MappedEntity(value = "participants")
public class Participant {

    @Id
    @GeneratedValue
    private Long id;
    @Nullable
    private String description;

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
    @JoinColumn(name = "team_id")
    private Team team;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @Relation(
            value = Relation.Kind.MANY_TO_MANY,
            cascade = Relation.Cascade.ALL
    )
    @JoinTable(
            name = "participant_skills",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Nullable
    private Set<Skill> skills;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "vacancy"
    )
    @Nullable
    private Set<TeamRequest> requests = new HashSet<>();

    public Participant(Long id,
                       @Nullable String description,
                       @Nullable User user,
                       Team team,
                       Direction direction,
                       @Nullable Set<Skill> skills,
                       @Nullable Set<TeamRequest> requests) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.team = team;
        this.direction = direction;
        this.skills = skills;
        this.requests = requests;
    }
}
