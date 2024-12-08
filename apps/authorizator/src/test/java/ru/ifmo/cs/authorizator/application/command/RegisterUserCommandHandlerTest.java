package ru.ifmo.cs.authorizator.application.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportFeignClient;
import ru.itmo.cs.command_bus.CommandHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegisterUserCommandHandlerTest {

    @Mock
    private PassportFeignClient passportClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private RegisterUserCommandHandler registerUserCommandHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(passportClient.create(anyString())).thenReturn(UserId.of(1));
    }

    @Test
    public void testHandle() {
        String roleSlug = "hr";
        String username = "testUser";
        String rawPassword = "rawPassword";
        String encodedPassword = "encodedPassword";

        RegisterUserCommand command = new RegisterUserCommand(username, rawPassword, roleSlug);
        UserId userId = UserId.of(1);

        when(passportClient.create(roleSlug)).thenReturn(userId);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        registerUserCommandHandler.handle(command);

        ArgumentCaptor<UserInfoAggregate> userInfoCaptor = ArgumentCaptor.forClass(UserInfoAggregate.class);
        verify(userInfoRepository).save(userInfoCaptor.capture());

        UserInfoAggregate capturedUserInfo = userInfoCaptor.getValue();
        assertEquals(userId, capturedUserInfo.getUserId());
        assertEquals(username, capturedUserInfo.getUsername());
    }
}