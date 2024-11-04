package ru.ifmo.cs.authorizator.application.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.jwt_auth.application.JwtGenerator;

@Service
@AllArgsConstructor
public class AuthorizationProcessor {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    public String authorize(String username, String password) {
        UserInfoAggregate user = userInfoRepository.findByUsername(username);
        if (user.isMatchAuthorizationCredentials(username, password, passwordEncoder)) {
            return jwtGenerator.generateFor(user.getUserId());
        }
        throw new IllegalArgumentException("Wrong password");
    }
}
