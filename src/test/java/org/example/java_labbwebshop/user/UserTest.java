package org.example.java_labbwebshop.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@tester.com");
        testUser.setPassword("Testing123");
        testUser.setRole(User.Role.USER);
    }

    @Test
    public void testRegisterUser() {
        User newUser = new User();
        newUser.setEmail("new@test.com");
        newUser.setPassword("Testing123");

        when(userRepository.save(newUser)).thenReturn(newUser);

        //Act
        userService.registerUser(newUser);

        //Assert
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testLoginUser() {
        String email = "test@test.com";
        String password = "Testing123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        //DO
        Optional<User> result = userService.login(email, password);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindUserById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(testUser));

        //Do
        Optional<User> result = userService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        assertEquals("test@tester.com", result.get().getEmail());
        verify(userRepository, times(1)).findById(id);
    }

}