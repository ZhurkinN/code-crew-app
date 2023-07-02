package cis.tinkoff.auth;

import java.util.List;

public class RefreshToken {

    private final String refreshToken;
    private final boolean revoked;
    private final String username;
    private final List<String> roles;

    public RefreshToken(String refreshToken, boolean revoked, String username, List<String> roles) {
        this.refreshToken = refreshToken;
        this.revoked = revoked;
        this.username = username;
        this.roles = roles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
