package cis.tinkoff.model.generic;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Introspected
public class GenericModel {

    @Id
    @GeneratedValue
    private Long id;

    @DateCreated
    @TypeDef(type = DataType.TIMESTAMP)
    private LocalDateTime createdWhen;
    private Boolean isDeleted;

    public GenericModel(Long id,
                        LocalDateTime createdWhen,
                        Boolean isDeleted) {
        this.id = id;
        this.createdWhen = createdWhen;
        this.isDeleted = isDeleted;
    }
}
