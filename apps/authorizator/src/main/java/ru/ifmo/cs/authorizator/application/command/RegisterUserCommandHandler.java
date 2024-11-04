package ru.ifmo.cs.authorizator.application.command;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportClient;
import ru.itmo.cs.command_bus.CommandHandler;

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

}
