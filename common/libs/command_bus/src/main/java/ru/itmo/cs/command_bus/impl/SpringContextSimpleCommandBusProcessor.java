package ru.itmo.cs.command_bus.impl;


import ru.itmo.cs.command_bus.Command;
import ru.itmo.cs.command_bus.CommandBusCommand;
import ru.itmo.cs.command_bus.CommandBusProcessor;
import ru.itmo.cs.command_bus.CommandHandler;

public class SpringContextSimpleCommandBusProcessor implements CommandBusProcessor {

    private final SpringContextHandlerResolver springContextHandlerResolver;

    public SpringContextSimpleCommandBusProcessor(
            SpringContextHandlerResolver springContextHandlerResolver
    ) {
        this.springContextHandlerResolver = springContextHandlerResolver;
    }

    public void process(CommandBusCommand commandBusCommand) {
        CommandHandler<? super Command> handler = springContextHandlerResolver.resolve(
                commandBusCommand.getCommand()
        );

        handler.handle(commandBusCommand.getCommand());
    }
}
