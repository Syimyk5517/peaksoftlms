package com.example.peaksoftlmsb8.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorResponse {
    private Long id;
    private String fullName;
    private String special;
    private String phoneNumber;
    private String email;
    private String password;
}
