package com.example.peaksoftlmsb8.dto.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    private Long id;
    private String password;
}