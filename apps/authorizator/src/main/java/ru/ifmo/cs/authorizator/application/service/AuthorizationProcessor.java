package ru.ifmo.cs.authorizator.application.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.jwt_token.application.JwtGenerator;
import ru.ifmo.cs.passport.api.PassportFeignClient;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationProcessor {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final PassportFeignClient passportClient;

    public String authorize(String username, String password) {
        UserInfoAggregate user = userInfoRepository.findByUsername(username);
        log.info("Found user: {}", user.toString());
        PassportUserResponseDto passportUser = passportClient.getUser(user.getUserId().getUid());
        log.info("Found passport user: {}", passportUser.toString());
        if (user.isMatchAuthorizationCredentials(username, password, passwordEncoder)) {
            return jwtGenerator.generateFor(user.getUserId());
        }
        throw new IllegalArgumentException("Wrong password");
    }
}
