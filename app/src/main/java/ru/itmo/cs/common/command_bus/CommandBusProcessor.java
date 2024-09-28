package ru.itmo.cs.common.command_bus;

public interface CommandBusProcessor {
    void process(CommandBusCommand commandBusCommand);
}
