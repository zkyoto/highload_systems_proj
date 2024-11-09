package ru.ifmo.cs.authorizator.application.command;

import lombok.AllArgsConstructor;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
public class RegisterUserCommand implements Command {
    final String username;
    final String password;
    final String roleSlug;
}
