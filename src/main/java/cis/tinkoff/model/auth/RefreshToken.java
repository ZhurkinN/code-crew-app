package cis.tinkoff.model.auth;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@MappedEntity
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String refreshToken;
    private Boolean revoked = false;

    @DateCreated
    private Instant dateCreated;

}
