package org.example.java_labbwebshop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateOrUpdateUserDto {
    private String email;
    private String password;
    private String role;
}
