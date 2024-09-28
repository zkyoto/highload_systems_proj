package ru.itmo.cs.common.command_bus;

public interface CommandBus {
    void submit(Command command);
}
