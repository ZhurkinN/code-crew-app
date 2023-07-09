package cis.tinkoff.controller.model.generic;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class GenericDTO implements Serializable {

    protected Long id;
    private Long createdWhen;
    protected Boolean isDeleted;
}
