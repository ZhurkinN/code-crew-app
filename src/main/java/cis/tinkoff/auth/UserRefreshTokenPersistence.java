package cis.tinkoff.auth;

import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.errors.OauthErrorResponseException;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;


import java.util.*;

import static io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT;

@Singleton
public class UserRefreshTokenPersistence implements RefreshTokenPersistence {

    // Note: old refresh tokens are never removed!
    private static final Map<String, RefreshToken> IN_MEMORY_REFRESH_TOKEN_STORE = new HashMap<>();


    @Override
    @EventListener
    public void persistToken(RefreshTokenGeneratedEvent event) {
        if (event != null &&
                event.getRefreshToken() != null &&
                event.getAuthentication() != null &&
                event.getAuthentication().getName() != null) {
            String payload = event.getRefreshToken();

            IN_MEMORY_REFRESH_TOKEN_STORE.put(payload,
                    new RefreshToken(event.getRefreshToken(),
                            false,
                            event.getAuthentication().getName(),
                            event.getAuthentication().getRoles() == null
                                    ? Collections.emptyList()
                                    : List.copyOf(event.getAuthentication().getRoles())
                    )
            );
        }
    }

    @Override
    public Publisher<Authentication> getAuthentication(String refreshToken) {
        return Flux.create(emitter -> {
            final RefreshToken existingRefreshToken = IN_MEMORY_REFRESH_TOKEN_STORE.get(refreshToken);

            if(existingRefreshToken != null){
                if(existingRefreshToken.isRevoked()){
                    emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null));
                } else {
                emitter.next(Authentication.build(existingRefreshToken.getUsername(),existingRefreshToken.getRoles()));
                emitter.complete();
                }
            }
            else{
                emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null));
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}