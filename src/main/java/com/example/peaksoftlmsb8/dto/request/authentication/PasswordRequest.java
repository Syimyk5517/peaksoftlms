package com.example.peaksoftlmsb8.dto.request.authentication;

import com.example.peaksoftlmsb8.validation.password.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    private Long id;
    @PasswordValid
    @NotBlank(message = "password can't be empty!")
    private String password;
}