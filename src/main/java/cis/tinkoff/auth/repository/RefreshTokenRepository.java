package cis.tinkoff.auth.repository;

import cis.tinkoff.auth.model.RefreshToken;
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

    RefreshToken saveRefreshToken(RefreshToken refreshToken);

    Optional<RefreshToken> findByRefreshToken(@NonNull @NotBlank String refreshToken);

    Optional<RefreshToken> findByUsername(@NonNull @NotBlank String username);

    void deleteByUsername(@NonNull @NotNull String username);

    @Query("UPDATE refresh_token SET revoked = TRUE WHERE(" +
            "SELECT COUNT(*) FROM refresh_token rt WHERE" +
            " rt.revoked = FALSE and rt.username = :username) > :maxCount - 1 AND revoked = FALSE")
    void checkAndUpdateActiveRefreshTokensByUsername(@NonNull @NotNull String username,
                                                     @Parameter("maxCount") @NonNull @NotNull Integer maxCountOfActiveRefreshTokens);
}