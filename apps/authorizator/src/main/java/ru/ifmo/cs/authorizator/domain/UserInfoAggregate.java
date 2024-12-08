package ru.ifmo.cs.authorizator.domain;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.authorizator.domain.value.UserInfoId;
import ru.ifmo.cs.misc.UserId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoAggregate {
    private final UserInfoId id;
    private final Instant createdAt;
    @NonNull
    private Instant updatedAt;
    private final UserId userId;
    @Getter(AccessLevel.NONE)
    private final UserAuthorizationCredentials authCreds;

    public static UserInfoAggregate create(
            UserId userId,
            String username,
            String password,
            PasswordEncoder passwordEncoder
    ) {
        Instant now = Instant.now();
        return new UserInfoAggregate(UserInfoId.generate(), now, now, userId,
                UserAuthorizationCredentials.create(username, password, passwordEncoder, now));
    }

    public boolean isMatchAuthorizationCredentials(
            String username,
            String password,
            PasswordEncoder passwordEncoder
    ) {
        return authCreds.isMatch(username, password, passwordEncoder);
    }

    public String getUsername() {
        return authCreds.getUsername();
    }
}
