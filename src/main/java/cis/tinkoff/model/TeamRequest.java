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

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "team_request")
public class TeamRequest {

    @Id
    @GeneratedValue
    private Long id;
    @Nullable
    private Boolean isAccepted;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "user_id")
    private User user;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.PERSIST
    )
    @JoinColumn(name = "vacancy_id")
    private Participant vacancy;

    public TeamRequest(Long id,
                       @Nullable Boolean isAccepted,
                       User user,
                       Resume resume,
                       Participant vacancy) {
        this.id = id;
        this.isAccepted = isAccepted;
        this.user = user;
        this.resume = resume;
        this.vacancy = vacancy;
    }
}
