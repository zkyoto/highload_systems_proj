package ru.ifmo.cs.passport.application.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.cs.passport.domain.PassportUserRepository;

class CreatePassportUserCommandHandlerTest {
    private CreatePassportUserCommandHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreatePassportUserCommandHandler(Mockito.mock(PassportUserRepository.class));
    }

    @Test
    void handleCommand() {
        handler.handle(Mockito.mock(CreatePassportUserCommand.class));
    }

}