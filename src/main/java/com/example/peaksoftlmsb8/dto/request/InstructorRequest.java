package com.example.peaksoftlmsb8.dto.request;

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
public class InstructorRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String special;
}
