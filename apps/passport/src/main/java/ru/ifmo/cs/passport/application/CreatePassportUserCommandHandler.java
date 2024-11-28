package ru.ifmo.cs.passport.application;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.misc.util.RandomNameGenerator;
import ru.ifmo.cs.misc.util.RandomUserIdGenerator;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.exception.DuplicateUserIdException;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class CreatePassportUserCommandHandler implements CommandHandler<CreatePassportUserCommand> {
    private final PassportUserRepository passportRepository;

    @Override
    public void handle(CreatePassportUserCommand command) {
        boolean isSucceeded = false;
        while (!isSucceeded) {
            isSucceeded = true;
            Name randomName = RandomNameGenerator.generateRandomName();
            UserId randomUid = RandomUserIdGenerator.generateRandomUserId();
            PassportUser user = PassportUser.create(randomUid, randomName, Arrays.asList(command.role));
            try {
                passportRepository.save(user);
            } catch (DuplicateUserIdException e) {
                isSucceeded = false;
            }
        }
    }

}