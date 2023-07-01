package cis.tinkoff.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "skills")
public class Skill {

    @Id
    @GeneratedValue
    private Long id;
    private String skillName;

    @Relation(
            value = Relation.Kind.MANY_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "skills"
    )
    @Nullable
    private Set<Resume> resumes;

    @Relation(
            value = Relation.Kind.MANY_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "skills"
    )
    @Nullable
    private Set<Participant> participants;

    public Skill(Long id,
                 String skillName,
                 @Nullable Set<Resume> resumes,
                 @Nullable Set<Participant> participants) {
        this.id = id;
        this.skillName = skillName;
        this.resumes = resumes;
        this.participants = participants;
    }
}
