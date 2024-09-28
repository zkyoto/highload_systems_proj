package ru.itmo.cs.common.command_bus.impl;

import ru.itmo.cs.common.command_bus.Command;
import ru.itmo.cs.common.command_bus.CommandBus;
import ru.itmo.cs.common.command_bus.CommandBusCommand;
import ru.itmo.cs.common.command_bus.CommandBusProcessor;

public class SimpleCommandBusService implements CommandBus {

    private final CommandBusProcessor commandBusProcessor;

    public SimpleCommandBusService(CommandBusProcessor commandBusProcessor) {
        this.commandBusProcessor = commandBusProcessor;
    }

    public void submit(Command command) {
        CommandBusCommand commandBusCommand = new CommandBusCommand(command);
        commandBusProcessor.process(commandBusCommand);
    }
}
