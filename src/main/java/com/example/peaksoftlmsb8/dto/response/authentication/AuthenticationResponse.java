package com.example.peaksoftlmsb8.dto.response.authentication;

import com.example.peaksoftlmsb8.db.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String  email;
    private Role role;
}
