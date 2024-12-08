package ru.ifmo.cs.authorizator.infrastructure.in_memory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.authorizator.domain.value.UserInfoId;
import ru.ifmo.cs.misc.UserId;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStubUserInfoRepositoryTest {

    private UserInfoRepository userInfoRepository;

    private UserInfoAggregate user1;
    private UserInfoAggregate user2;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("pwd");
        userInfoRepository = new InMemoryStubUserInfoRepository();

        user1 = UserInfoAggregate.create(UserId.of(1), "user1", "password1", passwordEncoder);

        user2 = UserInfoAggregate.create(UserId.of(1), "user2", "password2", passwordEncoder);

        userInfoRepository.save(user1);
        userInfoRepository.save(user2);
    }

    @Test
    public void testFindById() {
        UserInfoAggregate foundUser = userInfoRepository.findById(user1.getId());
        assertNotNull(foundUser);
        assertEquals(user1.getUsername(), foundUser.getUsername());
    }

    @Test
    public void testFindByUsername() {
        UserInfoAggregate foundUser = userInfoRepository.findByUsername("user1");
        assertNotNull(foundUser);
        assertEquals(user1.getId(), foundUser.getId());
    }

    @Test
    public void testFindAll() {
        List<UserInfoAggregate> allUsers = userInfoRepository.findAll();
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    public void testSaveNewUser() {
        UserInfoAggregate user3 = UserInfoAggregate.create(UserId.of(4), "user3", "password3", passwordEncoder);
        userInfoRepository.save(user3);

        UserInfoAggregate foundUser = userInfoRepository.findById(user3.getId());
        assertNotNull(foundUser);
        assertEquals("user3", foundUser.getUsername());
    }

    @Test
    public void testFindByIdNotFound() {
        UserInfoId nonExistentId = new UserInfoId(UUID.randomUUID());
        assertThrows(Exception.class, () -> userInfoRepository.findById(nonExistentId));
    }

    @Test
    public void testFindByUsernameNotFound() {
        assertThrows(Exception.class, () -> userInfoRepository.findByUsername("nonExistentUser"));
    }
}