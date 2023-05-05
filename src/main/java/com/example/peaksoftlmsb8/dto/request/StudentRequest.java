package com.example.peaksoftlmsb8.dto.request;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.enums.FormLearning;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Email(message = "Invalid email!")
    private String email;
    @Size(min = 5, max = 100, message = "Password must be at least 4 characters!")
    @NotBlank(message = "Password can't be empty!")
    private String password;
//    @Pattern(regexp = "\\d+")
    private Long groupId;
//    @NotBlank(message = "Form learning should not be null")
    private FormLearning formLearning;
}
