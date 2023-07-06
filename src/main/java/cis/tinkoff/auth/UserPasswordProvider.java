package cis.tinkoff.auth;

import cis.tinkoff.model.User;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.support.helper.CredentialsValidator;
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

@Singleton
@RequiredArgsConstructor
public class UserPasswordProvider implements AuthenticationProvider {

    private final static List<String> BASIC_ROLES = List.of("USER_ROLE");

    private final UserRepository userRepository;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {

        return Flux.create(emitter -> {

            String login = (String) authenticationRequest.getIdentity();
            String password = (String) authenticationRequest.getSecret();
            Optional<User> userOptional = userRepository.findByEmail(login);
            boolean isValid = userOptional.isPresent()
                    && CredentialsValidator.validCredentials(password, userOptional.get());

            if (!isValid) {
                emitter.error(AuthenticationResponse.exception("Auth error"));
            } else
                emitter.next(AuthenticationResponse.success(userOptional.get().getEmail(), BASIC_ROLES));
            emitter.complete();
        }, FluxSink.OverflowStrategy.ERROR);
    }

}
