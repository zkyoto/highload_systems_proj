package ru.itmo.cs.command_bus.exception;

public class CommandHandlerNotExistException extends RuntimeException {
    public CommandHandlerNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandHandlerNotExistException(String message) {
        super(message);
    }
}
