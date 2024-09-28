package ru.itmo.cs.command_bus;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
