package com.example.peaksoftlmsb8.dto.request.authentication;

import com.example.peaksoftlmsb8.validation.password.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    private Long id;
    @NotBlank(message = "Пароль не должна быть пустым.")
    @NotNull(message = "Пароль не должна быть пустым.")
    private String password;
}