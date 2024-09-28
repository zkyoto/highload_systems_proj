package ru.itmo.cs.common.command_bus.exception;

public class CommandHandlerNotExistException extends RuntimeException {
    public CommandHandlerNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandHandlerNotExistException(String message) {
        super(message);
    }
}
