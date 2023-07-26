package cis.tinkoff.support.exceptions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO implements Serializable {

    private String path;
    private String message;
    private String loggedMessage;
    private int statusCode;
    private Long time = System.currentTimeMillis();

}
