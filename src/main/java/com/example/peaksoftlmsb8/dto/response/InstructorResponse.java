package com.example.peaksoftlmsb8.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String special;
    private String phoneNumber;
    private String email;
    private String password;
}
