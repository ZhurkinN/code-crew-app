package cis.tinkoff.auth;

import cis.tinkoff.model.User;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.repository.auth.RefreshTokenRepository;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.Optional;

import static cis.tinkoff.auth.enumerated.UserRole.USER_ROLE;
import static cis.tinkoff.support.exceptions.constants.LoggedErrorMessageKeeper.AUTHENTICATION_ERROR;

@Singleton
@RequiredArgsConstructor
public class UserPasswordProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Property(name = "micronaut.security.token.jwt.generator.refresh-token.max-token-count")
    private int activeRefreshTokensCount;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {

            String login = (String) authenticationRequest.getIdentity();
            String password = (String) authenticationRequest.getSecret();
            Optional<User> userOptional = userRepository.findByEmailAndIsDeletedFalse(login);

            boolean isValid = userOptional.isPresent()
                    && userOptional.get().getPassword().equals(password)
                    && !userOptional.get().getIsDeleted();

            if (!isValid) {
                emitter.error(AuthenticationResponse.exception(AUTHENTICATION_ERROR));
            } else
                refreshTokenRepository.checkAndUpdateActiveRefreshTokensByUsername(login, activeRefreshTokensCount);
            emitter.next(AuthenticationResponse.success(userOptional.get().getEmail(), List.of(USER_ROLE.name())));
            emitter.complete();
        }, FluxSink.OverflowStrategy.ERROR);
    }

}
