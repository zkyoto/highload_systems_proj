package ru.ifmo.cs.authorizator.application.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.jwt_token.application.JwtGenerator;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportFeignClient;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorizationProcessorTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtGenerator jwtGenerator;

    @Mock
    private PassportFeignClient passportClient;

    @InjectMocks
    private AuthorizationProcessor authorizationProcessor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthorize_Success() {
        String username = "testUser";
        String rawPassword = "correctPassword";
        String token = "jwtToken";

        UserInfoAggregate user = mock(UserInfoAggregate.class);
        when(user.getUserId()).thenReturn(UserId.of(1));
        PassportUserResponseDto passportUser = mock(PassportUserResponseDto.class);

        when(userInfoRepository.findByUsername(username)).thenReturn(user);
        when(passportClient.getUser(anyLong())).thenReturn(passportUser);
        when(user.isMatchAuthorizationCredentials(username, rawPassword, passwordEncoder)).thenReturn(true);
        when(jwtGenerator.generateFor(any())).thenReturn(token);

        String result = authorizationProcessor.authorize(username, rawPassword);

        assertEquals(token, result);
        verify(userInfoRepository, times(1)).findByUsername(username);
        verify(passportClient, times(1)).getUser(anyLong());
        verify(jwtGenerator, times(1)).generateFor(any());
    }

    @Test
    public void testAuthorize_FailedIncorrectPassword() {
        String username = "testUser";
        String rawPassword = "incorrectPassword";

        UserInfoAggregate user = mock(UserInfoAggregate.class);
        when(user.getUserId()).thenReturn(UserId.of(1));
        PassportUserResponseDto passportUser = mock(PassportUserResponseDto.class);

        when(userInfoRepository.findByUsername(username)).thenReturn(user);
        when(passportClient.getUser(anyLong())).thenReturn(passportUser);
        when(user.isMatchAuthorizationCredentials(username, rawPassword, passwordEncoder)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorizationProcessor.authorize(username, rawPassword);
        });

        assertEquals("Wrong password", exception.getMessage());
        verify(userInfoRepository, times(1)).findByUsername(username);
        verify(passportClient, times(1)).getUser(anyLong());
        verify(jwtGenerator, never()).generateFor(any());
    }
}