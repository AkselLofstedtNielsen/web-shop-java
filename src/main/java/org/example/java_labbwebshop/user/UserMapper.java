package org.example.java_labbwebshop.user;

import org.example.java_labbwebshop.user.dto.UserDto;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public static User fromDto(CreateOrUpdateUserDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role((dto.getRole() != null && !dto.getRole().isBlank())
                        ? User.Role.valueOf(dto.getRole().toUpperCase())
                        : User.Role.USER)
                .build();
    }
}
