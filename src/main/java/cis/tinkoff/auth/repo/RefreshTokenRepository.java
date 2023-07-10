package cis.tinkoff.auth.repo;

import cis.tinkoff.auth.model.RefreshTokenEntity;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2, dataSource = "warehouse")
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

    RefreshTokenEntity saveRefreshToken(RefreshTokenEntity refreshTokenEntity);

    Optional<RefreshTokenEntity> findByRefreshToken(@NonNull @NotBlank String refreshToken);

    Optional<RefreshTokenEntity> findByUsername(@NonNull @NotBlank String username);

    void deleteByUsername(@NonNull @NotNull String username);

    RefreshTokenEntity updateRefreshToken(RefreshTokenEntity refreshTokenEntity);
}