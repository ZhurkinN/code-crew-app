package cis.tinkoff.model.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Introspected
public class GenericModel {

    @Id
    @GeneratedValue
    private Long id;

    @DateCreated
    @TypeDef(type = DataType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdWhen = LocalDateTime.now();
    private Boolean isDeleted = false;

    public GenericModel(Long id,
                        LocalDateTime createdWhen,
                        Boolean isDeleted) {
        this.id = id;
        this.createdWhen = createdWhen;
        this.isDeleted = isDeleted;
    }
}
