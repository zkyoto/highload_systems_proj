package ru.ifmo.cs.passport.application;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class CreatePassportUserCommand implements Command {
    Role role;
}
