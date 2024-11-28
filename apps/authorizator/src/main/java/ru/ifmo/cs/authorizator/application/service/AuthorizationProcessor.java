package ru.ifmo.cs.authorizator.application.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.jwt_auth.application.JwtGenerator;
import ru.ifmo.cs.passport.api.PassportClient;
import ru.ifmo.cs.passport.api.domain.PassportUser;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationProcessor {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final PassportClient passportClient;

    public String authorize(String username, String password) {
        UserInfoAggregate user = userInfoRepository.findByUsername(username);
        log.info("Found user: {}", user.toString());
        PassportUser passportUser = passportClient.findPassportUser(user.getUserId());
        log.info("Found passport user: {}", passportUser.toString());
        if (user.isMatchAuthorizationCredentials(username, password, passwordEncoder)) {
            return jwtGenerator.generateFor(user.getUserId());
        }
        throw new IllegalArgumentException("Wrong password");
    }
}
