package com.example.peaksoftlmsb8.dto.response;

import lombok.*;

@Getter
@Setter
public class InstructorResponse {
    private Long id;
    private String fullName;
    private String special;
    private String phoneNumber;
    private String email;

    public InstructorResponse(Long id, String fullName, String special, String phoneNumber, String email) {
        this.id = id;
        this.fullName = fullName;
        this.special = special;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
