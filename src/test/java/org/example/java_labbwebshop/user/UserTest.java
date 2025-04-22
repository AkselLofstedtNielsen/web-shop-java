package org.example.java_labbwebshop.user;

import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.example.java_labbwebshop.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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
        // Arrange
        CreateOrUpdateUserDto dto = new CreateOrUpdateUserDto();
        dto.setEmail("new@test.com");
        dto.setPassword("Testing123");
        dto.setRole("USER");

        User expectedUser = new User();
        expectedUser.setEmail(dto.getEmail());
        expectedUser.setPassword(dto.getPassword());
        expectedUser.setRole(User.Role.USER);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        UserDto result = userService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals("USER", result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        assertEquals("test@tester.com", result.get().getEmail());
        verify(userRepository, times(1)).findById(id);
    }

}
