package com.example.peaksoftlmsb8.dto.request.student;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.validation.email.EmailValid;
import com.example.peaksoftlmsb8.validation.phoneNumber.PhoneNumberValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    @NotBlank(message = "First name should not be null")
    @NotNull(message = "First name can't be empty")
    private String firstName;
    @NotBlank(message = "Last name should not be null")
    @NotNull(message = "Last name can't be empty")
    private String lastName;
    @NotBlank(message = "Phone number should not be null")
    @NotNull(message = "Phone number can't be empty")
    @PhoneNumberValid(message = "The phone number must not exceed 13 digits and must start with +996")
    private String phoneNumber;
    @NotBlank(message = "Email should not be null")
    @NotNull(message = "Email can't be empty")
    @EmailValid(message = "Email must contain @ and must end with .com")
    private String email;
    @NotBlank(message = "Group id should not be null")
    @NotNull(message = "Group id can't be empty")
    private Long groupId;
    private FormLearning formLearning;
    @NotBlank(message = "Link should not be null")
    @NotNull(message = "Link can't be empty")
    private String link;
}
