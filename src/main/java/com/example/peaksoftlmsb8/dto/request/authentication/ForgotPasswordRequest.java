package com.example.peaksoftlmsb8.dto.request.authentication;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    @Email
    private String email;
    private String link;

}
