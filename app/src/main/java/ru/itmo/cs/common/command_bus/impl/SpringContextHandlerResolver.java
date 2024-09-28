package ru.itmo.cs.common.command_bus.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.ResolvableType;
import ru.itmo.cs.common.command_bus.Command;
import ru.itmo.cs.common.command_bus.CommandHandler;
import ru.itmo.cs.common.command_bus.exception.CommandHandlerConflictException;
import ru.itmo.cs.common.command_bus.exception.CommandHandlerNotExistException;


public class SpringContextHandlerResolver implements ApplicationListener<ContextRefreshedEvent> {

    private final Map<Class<?>, String> handlerBeansByCommandMap = new HashMap<>();
    private final ApplicationContext applicationContext;

    public SpringContextHandlerResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        handlerBeansByCommandMap.clear();
        Map<String, CommandHandler> handlerBeans = applicationContext.getBeansOfType(CommandHandler.class);

        for (Map.Entry<String, CommandHandler> entry : handlerBeans.entrySet()) {
            Class<?> commandName = ResolvableType.forClass(AopUtils.getTargetClass(entry.getValue()))
                    .as(CommandHandler.class)
                    .getGeneric(0)
                    .resolve();

            if (handlerBeansByCommandMap.containsKey(commandName)) {
                throw new CommandHandlerConflictException(
                        String.format(
                                "There are more then 1 concrete handler have been found for command: %s. " +
                                        "Handler Bean id '%s' conflicts with bean id '%s'",
                                commandName,
                                handlerBeansByCommandMap.get(commandName),
                                entry.getKey()
                        )
                );
            }
            handlerBeansByCommandMap.put(commandName, entry.getKey());
        }
    }

    public CommandHandler<? super Command> resolve(Command command) {
        Class<?> className = command.getClass();

        if (!handlerBeansByCommandMap.containsKey(className)) {
            throw new CommandHandlerNotExistException(
                    "Command handler with name " + className.getSimpleName() + " not exists"
            );
        }

        return (CommandHandler<? super Command>) applicationContext.getBean(handlerBeansByCommandMap.get(className));
    }
}
