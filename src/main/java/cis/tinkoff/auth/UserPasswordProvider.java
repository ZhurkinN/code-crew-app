package cis.tinkoff.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.*;

@Singleton
public class UserPasswordProvider implements AuthenticationProvider {

    private static final Map<String, User> IN_MEMORY_USERS = Map.of(
            "bob", new User("bob", "bob123", List.of("ROLE_USER")),
            "cindy", new User("cindy", "cindy123", List.of("ROLE_USER")),
            "admin", new User("admin", "admin123", List.of("ROLE_USER", "ROLE_ADMIN"))
    );

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {

        return Flux.create(emitter -> {
            User user = IN_MEMORY_USERS.get(authenticationRequest.getIdentity());

            if(!checkPassword(authenticationRequest, user)){
                emitter.error(AuthenticationResponse.exception("Auth error"));
            }
            else
                emitter.next(AuthenticationResponse.success(user.getUsername(),user.getRoles()));
                emitter.complete();
        }, FluxSink.OverflowStrategy.ERROR);
    }

    private Boolean checkPassword(AuthenticationRequest <?, ?> authenticationRequest ,User user){
        return authenticationRequest.getSecret().equals(user.getPassword());
    }
}
