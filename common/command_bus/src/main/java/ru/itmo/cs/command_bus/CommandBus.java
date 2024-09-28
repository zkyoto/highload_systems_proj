package ru.itmo.cs.command_bus;

public interface CommandBus {
    void submit(Command command);
}
