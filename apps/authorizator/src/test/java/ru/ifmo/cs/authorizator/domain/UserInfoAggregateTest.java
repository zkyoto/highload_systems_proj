package ru.ifmo.cs.authorizator.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.value.UserInfoId;
import ru.ifmo.cs.misc.UserId;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserInfoAggregateTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserInfoId userInfoId;
    private Instant now;
    private UserId userId;
    private String username;
    private String password;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userInfoId = UserInfoId.generate();
        now = Instant.now();
        userId = UserId.of(1);
        username = "testUser";
        password = "testPassword";
    }

    @Test
    public void testCreate() {
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        UserInfoAggregate userInfo = UserInfoAggregate.create(userId, username, password, passwordEncoder);

        assertNotNull(userInfo);
        assertEquals(userId, userInfo.getUserId());
        assertEquals(username, userInfo.getUsername());
        assertNotNull(userInfo.getId());
        assertNotNull(userInfo.getCreatedAt());
        assertNotNull(userInfo.getUpdatedAt());
        verify(passwordEncoder, times(1)).encode(password);
    }

    @Test
    public void testIsMatchAuthorizationCredentials() {
        // Use the same password encoder configuration as in the creation
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        UserInfoAggregate userInfo = UserInfoAggregate.create(userId, username, password, passwordEncoder);

        boolean matches = userInfo.isMatchAuthorizationCredentials(username, password, passwordEncoder);
        assertTrue(matches, "The credentials should match the encoded password and username");

        // Test with incorrect password
        when(passwordEncoder.matches("wrongPassword", encodedPassword)).thenReturn(false);
        boolean wrongPasswordMatch = userInfo.isMatchAuthorizationCredentials(username, "wrongPassword", passwordEncoder);
        assertFalse(wrongPasswordMatch, "The credentials should not match with an incorrect password");
    }
}