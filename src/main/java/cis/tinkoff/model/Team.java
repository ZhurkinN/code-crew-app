package cis.tinkoff.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.jdbc.annotation.JoinColumn;
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
@MappedEntity(value = "team")
public class Team {

    @Id
    @GeneratedValue
    private Long id;

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
    @JoinColumn(name = "status_id")
    private TeamStatus teamStatus;

    private String theme;
    @Nullable
    private String description;
    private Integer programmersCount;
    private Boolean isVisible;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "team"
    )
    @Nullable
    private Set<Participant> participants = new HashSet<>();

    public Team(Long id,
                @Nullable User user,
                TeamStatus teamStatus,
                String theme,
                @Nullable String description,
                Integer programmersCount,
                Boolean isVisible,
                @Nullable Set<Participant> participants) {
        this.id = id;
        this.user = user;
        this.teamStatus = teamStatus;
        this.theme = theme;
        this.description = description;
        this.programmersCount = programmersCount;
        this.isVisible = isVisible;
        this.participants = participants;
    }
}
