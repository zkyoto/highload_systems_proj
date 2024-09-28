package ru.itmo.cs.common.command_bus;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
