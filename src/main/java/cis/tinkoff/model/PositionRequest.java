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

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "position_request")
public class PositionRequest extends GenericModel {

    @Nullable
    private String coverLetter;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @JoinColumn(name = "status")
    private RequestStatus requestStatus;

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
    @JoinColumn(name = "position_id")
    private Position position;

    public PositionRequest(Long id,
                           LocalDateTime createdWhen,
                           Boolean isDeleted,
                           @Nullable String coverLetter,
                           RequestStatus requestStatus,
                           Resume resume,
                           Position position) {
        super(id, createdWhen, isDeleted);
        this.coverLetter = coverLetter;
        this.requestStatus = requestStatus;
        this.resume = resume;
        this.position = position;
    }
}
