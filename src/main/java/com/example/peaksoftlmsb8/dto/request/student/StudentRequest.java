package com.example.peaksoftlmsb8.dto.request.student;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.validation.email.EmailValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    @NotBlank(message = "First name can't be empty!")
    private String firstName;
    @NotBlank(message = "Last name can't be empty!")
    private String lastName;
    @NotBlank(message = "Phone number can't be empty!")
    @Pattern(regexp = "\\+996\\d{9}", message = "Invalid phone number format!")
    private String phoneNumber;
    @NotBlank(message = "E-mail can't be empty!")
    @EmailValid
    private String email;
    private Long groupId;
    private FormLearning formLearning;
    private String link;
}
