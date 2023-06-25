package com.example.peaksoftlmsb8.dto.request.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Электронная почта не должна быть пустым.")
    @NotNull(message = "Электронная почта не должна быть пустым.")
    @Email(message = "Электронная почта должна содержать @ ")
    private String email;
    @NotBlank(message = "Пароль не должна быть пустым.")
    @NotNull(message = "Пароль не должна быть пустым.")
    private String password;
}
