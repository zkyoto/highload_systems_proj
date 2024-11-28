package ru.ifmo.cs.authorizator.application.command;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportClient;
import ru.itmo.cs.command_bus.CommandHandler;

@Slf4j
@Service
@AllArgsConstructor
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand> {
    private final PassportClient passportClient;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;

    @Override
    public void handle(RegisterUserCommand command) {
        UserId userId = passportClient.createUser(command.roleSlug);
        UserInfoAggregate userInfoAggregate = UserInfoAggregate.create(
                userId,
                command.username,
                command.password,
                passwordEncoder
        );
        userInfoRepository.save(userInfoAggregate);
    }

    @PostConstruct
    public void initAdmin() {
        UserId userId = passportClient.createUser("supervisor");
        log.info("Created user id: {}", userId);
        UserInfoAggregate userInfoAggregate = UserInfoAggregate.create(
                userId,
                "z",
                "z",
                passwordEncoder
        );
        userInfoRepository.save(userInfoAggregate);
    }

}
