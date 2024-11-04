package ru.ifmo.cs.authorizator.domain;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.authorizator.domain.value.UserAuthorizationCredentialsId;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthorizationCredentials {
    private final UserAuthorizationCredentialsId id;
    private final Instant createdAt;
    @NonNull private Instant updatedAt;
    @NonNull private String username;
    @NonNull private String password;

    static UserAuthorizationCredentials create(
            String username,
            String password,
            PasswordEncoder passwordEncoder,
            Instant creationTime
    ) {
        return new UserAuthorizationCredentials(
                UserAuthorizationCredentialsId.generate(),
                creationTime,
                creationTime,
                username,
                passwordEncoder.encode(password)
        );
    }

    boolean isMatch(
            String username,
            String password,
            PasswordEncoder passwordEncoder
    ) {
        return this.username.equals(username) && passwordEncoder.matches(password, this.password);
    }

    void changePassword(
            String newPassword,
            PasswordEncoder passwordEncoder
    ) {
        this.password = passwordEncoder.encode(newPassword);
        this.updatedAt = Instant.now();
    }
}
