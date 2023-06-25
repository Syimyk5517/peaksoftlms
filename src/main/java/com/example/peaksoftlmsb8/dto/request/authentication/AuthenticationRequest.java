package com.example.peaksoftlmsb8.dto.request.authentication;
import com.example.peaksoftlmsb8.validation.password.PasswordValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @PasswordValid
    private String password;
}
