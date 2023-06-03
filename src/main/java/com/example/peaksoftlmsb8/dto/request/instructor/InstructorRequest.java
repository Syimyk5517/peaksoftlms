package com.example.peaksoftlmsb8.dto.request.instructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String special;
    private String link;
}
