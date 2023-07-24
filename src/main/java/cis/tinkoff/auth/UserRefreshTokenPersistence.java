package cis.tinkoff.auth;

import cis.tinkoff.model.auth.RefreshToken;
import cis.tinkoff.repository.auth.RefreshTokenRepository;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.errors.OauthErrorResponseException;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Optional;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.REFRESH_TOKEN_NOT_FOUND;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.REFRESH_TOKEN_REVOKED;
import static io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT;

@Singleton
@RequiredArgsConstructor
public class UserRefreshTokenPersistence implements RefreshTokenPersistence {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @EventListener
    public void persistToken(RefreshTokenGeneratedEvent event) {
        if (event != null &&
                event.getRefreshToken() != null &&
                event.getAuthentication() != null &&
                event.getAuthentication().getName() != null) {
            RefreshToken token = new RefreshToken()
                    .setUsername(event.getAuthentication().getName())
                    .setRefreshToken(event.getRefreshToken());

            refreshTokenRepository.save(token);
        }
    }

    @Override
    public Publisher<Authentication> getAuthentication(String refreshToken) {
        return Flux.create(emitter -> {
            Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken);

            if (tokenOpt.isEmpty()) {
                emitter.error(new OauthErrorResponseException(
                        INVALID_GRANT,
                        REFRESH_TOKEN_NOT_FOUND,
                        null)
                );
            }

            RefreshToken token = tokenOpt.get();
            if (token.getRevoked()) {

                emitter.error(new OauthErrorResponseException(
                        INVALID_GRANT,
                        REFRESH_TOKEN_REVOKED,
                        null)
                );

            } else {
                emitter.next(Authentication.build(token.getUsername()));
                emitter.complete();
            }

        }, FluxSink.OverflowStrategy.ERROR);
    }
}