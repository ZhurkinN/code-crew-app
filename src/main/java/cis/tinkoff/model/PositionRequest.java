package cis.tinkoff.model;

import cis.tinkoff.model.generic.GenericModel;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Relation;
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
    private Boolean isInvite;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "status")
    @Nullable
    private RequestStatusDictionary status;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "resume_id")
    @Nullable
    private Resume resume;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "position_id")
    @Nullable
    private Position position;

    public PositionRequest(Long id,
                           LocalDateTime createdWhen,
                           Boolean isDeleted,
                           @Nullable String coverLetter,
                           Boolean isInvite,
                           @Nullable RequestStatusDictionary status,
                           @Nullable Resume resume,
                           @Nullable Position position) {
        super(id, createdWhen, isDeleted);
        this.coverLetter = coverLetter;
        this.isInvite = isInvite;
        this.status = status;
        this.resume = resume;
        this.position = position;
    }
}
