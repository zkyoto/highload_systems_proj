package ru.itmo.cs.common.command_bus;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;

@Getter
public class CommandBusCommand {
    private final UUID commandId;
    private final Instant submittedAt;
    private final Command command;

    public CommandBusCommand(Command command) {
        this.commandId = UUID.randomUUID();
        this.submittedAt = Instant.now();
        this.command = command;
    }
}
