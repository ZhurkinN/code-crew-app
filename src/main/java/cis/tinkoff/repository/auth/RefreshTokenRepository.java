package cis.tinkoff.repository.auth;

import cis.tinkoff.model.auth.RefreshToken;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(@NonNull @NotBlank String refreshToken);

    @Query("UPDATE refresh_token SET revoked = TRUE WHERE(" +
            "SELECT COUNT(*) FROM refresh_token rt WHERE" +
            " rt.revoked = FALSE and rt.username = :username) > :maxCount - 1 AND revoked = FALSE")
    void checkAndUpdateActiveRefreshTokensByUsername(@NonNull @NotNull String username,
                                                     @Parameter("maxCount") @NonNull @NotNull int maxCountOfActiveRefreshTokens);
}
