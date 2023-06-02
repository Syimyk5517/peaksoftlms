package com.example.peaksoftlmsb8.dto.request.authentication;

import com.example.peaksoftlmsb8.validation.email.EmailValid;
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
    @EmailValid
    @NotBlank(message = "email can't be empty!")
    private String email;
    @PasswordValid
    @NotBlank(message = "password can't be empty!")
    private String password;
}
