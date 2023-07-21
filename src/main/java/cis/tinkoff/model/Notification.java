package cis.tinkoff.model;

import cis.tinkoff.support.helper.TimestampAttributeConverter;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.*;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "notification")
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "type")
    @Nullable
    private NotificationTypeDictionary type;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "request_id")
    @Nullable
    private PositionRequest request;

    @Relation(
            value = Relation.Kind.MANY_TO_ONE,
            cascade = Relation.Cascade.NONE
    )
    @MappedProperty(value = "user_id")
    @Nullable
    private User user;

    @TypeDef(type = DataType.TIMESTAMP, converter = TimestampAttributeConverter.class)
    private Long createdWhen = System.currentTimeMillis();

    public Notification(Long id,
                        @Nullable NotificationTypeDictionary type,
                        @Nullable PositionRequest request,
                        @Nullable User user,
                        Long createdWhen) {
        this.id = id;
        this.type = type;
        this.request = request;
        this.user = user;
        this.createdWhen = createdWhen;
    }
}
