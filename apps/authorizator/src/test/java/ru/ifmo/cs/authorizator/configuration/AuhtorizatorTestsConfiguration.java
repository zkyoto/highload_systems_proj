package ru.ifmo.cs.authorizator.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.authorizator.application.command.RegisterUserCommandHandler;
import ru.ifmo.cs.authorizator.application.service.AuthorizationProcessor;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.jwt_auth.application.JwtGenerator;
import ru.ifmo.cs.passport.api.stub.PassportFeignClientStub;

@TestConfiguration
public class AuhtorizatorTestsConfiguration {

    @Primary
    @Bean
    public RegisterUserCommandHandler registerUserCommandHandler(
            PasswordEncoder passwordEncoder,
            UserInfoRepository userInfoRepository
    ) {
        return new RegisterUserCommandHandler(
                new PassportFeignClientStub(),
                passwordEncoder,
                userInfoRepository
        );
    }

    @Primary
    @Bean
    public AuthorizationProcessor authorizationProcessor(
            UserInfoRepository userInfoRepository,
            PasswordEncoder passwordEncoder,
            JwtGenerator jwtGenerator
    ) {
        return new AuthorizationProcessor(
                userInfoRepository,
                passwordEncoder,
                jwtGenerator,
                new PassportFeignClientStub()
        );
    }
}
