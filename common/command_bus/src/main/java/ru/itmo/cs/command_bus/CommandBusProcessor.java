package ru.itmo.cs.command_bus;

public interface CommandBusProcessor {
    void process(CommandBusCommand commandBusCommand);
}
