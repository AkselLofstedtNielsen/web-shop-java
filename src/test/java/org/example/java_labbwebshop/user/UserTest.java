package org.example.java_labbwebshop.user;

import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.example.java_labbwebshop.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void findByRole_ShouldReturnUsers_WhenRoleIsValid() {
        // given
        User user = User.builder().id(1L).email("test@example.com").role(User.Role.USER).build();
        UserDto userDto = UserDto.builder().id(1L).email("test@example.com").role("USER").build();

        when(userRepository.findByRole(User.Role.USER)).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // when
        ResponseEntity<?> response = userService.findByRole("USER");

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody())
                .asInstanceOf(list(UserDto.class))
                .containsExactly(userDto);
    }

    @Test
    void findByRole_ShouldReturnBadRequest_WhenRoleIsInvalid() {
        // when
        ResponseEntity<?> response = userService.findByRole("INVALID");

        // then
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        assertThat(response.getBody()).isEqualTo("Invalid role. Valid roles are: USER, ADMIN");
        verifyNoInteractions(userRepository, userMapper);
    }

    @Test
    void create_ShouldEncodePasswordAndReturnDto() {
        // given
        CreateOrUpdateUserDto dto = new CreateOrUpdateUserDto("test@example.com", "rawpass", "USER");
        User user = User.builder().id(1L).email(dto.getEmail()).password("rawpass").role(User.Role.USER).build();
        User savedUser = User.builder().id(1L).email(dto.getEmail()).password("encoded").role(User.Role.USER).build();
        UserDto userDto = UserDto.builder().id(1L).email(dto.getEmail()).role("USER").build();

        when(userMapper.fromDto(dto)).thenReturn(user);
        when(passwordEncoder.encode("rawpass")).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userDto);

        // when
        UserDto result = userService.create(dto);

        // then
        assertThat(result).isEqualTo(userDto);
        verify(passwordEncoder).encode("rawpass");
        verify(userRepository).save(user);
    }

    @Test
    void update_ShouldReturnNull_WhenUserDoesNotExist() {
        // given
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // when
        UserDto result = userService.update(99L, new CreateOrUpdateUserDto("x", "y", "USER"));

        // then
        assertThat(result).isNull();
    }
}
