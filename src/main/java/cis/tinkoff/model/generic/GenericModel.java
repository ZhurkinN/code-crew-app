package cis.tinkoff.model.generic;

import cis.tinkoff.support.helper.TimestampAttributeConverter;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Introspected
public class GenericModel {

    @Id
    @GeneratedValue
    private Long id;

    @TypeDef(type = DataType.TIMESTAMP, converter = TimestampAttributeConverter.class)
    private Long createdWhen = System.currentTimeMillis();
    private Boolean isDeleted = false;

    public GenericModel(Long id,
                        Long createdWhen,
                        Boolean isDeleted) {
        this.id = id;
        this.createdWhen = createdWhen;
        this.isDeleted = isDeleted;
    }
}
