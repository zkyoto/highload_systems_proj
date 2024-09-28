package ru.itmo.cs.command_bus.exception;

public class CommandHandlerConflictException extends RuntimeException {
    public CommandHandlerConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandHandlerConflictException(String message) {
        super(message);
    }
}
