package cis.tinkoff.support.helper;

import cis.tinkoff.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CredentialsValidator {

    public static boolean validCredentials(String givenPassword,
                                           User user) {
        return user.getPassword().equals(givenPassword);
    }
}
