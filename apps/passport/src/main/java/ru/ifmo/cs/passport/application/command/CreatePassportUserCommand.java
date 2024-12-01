package ru.ifmo.cs.passport.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.misc.util.RandomNameGenerator;
import ru.ifmo.cs.misc.util.RandomUserIdGenerator;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class CreatePassportUserCommand implements Command {
    UserId userId;
    Name name;
    Role role;
}
