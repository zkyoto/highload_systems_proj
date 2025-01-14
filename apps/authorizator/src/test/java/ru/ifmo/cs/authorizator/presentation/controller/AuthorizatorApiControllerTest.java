package ru.ifmo.cs.authorizator.presentation.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.ifmo.cs.authorizator.application.command.RegisterUserCommand;
import ru.ifmo.cs.authorizator.application.service.AuthorizationProcessor;
import ru.ifmo.cs.authorizator.presentation.controller.dto.AuthorizedUserTokenResponseBodyDto;
import ru.ifmo.cs.authorizator.presentation.controller.dto.RegisterUserRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorizatorApiControllerTest {

    @Mock
    private CommandBus commandBus;

    @Mock
    private AuthorizationProcessor authorizationProcessor;

    @InjectMocks
    private AuthorizatorApiController authorizatorApiController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        RegisterUserRequestBodyDto requestDto = new RegisterUserRequestBodyDto("testUser", "testPassword", "userRole");

        ResponseEntity<?> response = authorizatorApiController.registerUser(requestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        ArgumentCaptor<RegisterUserCommand> captor = ArgumentCaptor.forClass(RegisterUserCommand.class);
        verify(commandBus, times(1)).submit(captor.capture());
    }

    @Test
    public void testAuthorize() {
        String username = "testUser";
        String password = "testPassword";
        String jwtToken = "jwtToken";

        when(authorizationProcessor.authorize(username, password)).thenReturn(jwtToken);

        ResponseEntity<?> response = authorizatorApiController.authorize(username, password);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        AuthorizedUserTokenResponseBodyDto responseBody = (AuthorizedUserTokenResponseBodyDto) response.getBody();
        assertNotNull(responseBody);
        assertEquals(jwtToken, responseBody.token());

        verify(authorizationProcessor, times(1)).authorize(username, password);
    }
}