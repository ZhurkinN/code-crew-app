package cis.tinkoff.controller.model.generic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public abstract class GenericDTO implements Serializable {

    @JsonInclude
    protected Long id;

    @JsonIgnore
    private Long createdWhen;

    @JsonIgnore
    protected Boolean isDeleted;
}