package cis.tinkoff.auth.model;

import java.util.List;

public record RefreshToken(
        String refreshToken,
        boolean revoked,
        String username,
        List<String> roles
) {

}
