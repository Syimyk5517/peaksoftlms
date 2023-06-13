package com.example.peaksoftlmsb8.dto.request.student;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.validation.phoneNumber.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    @NotBlank(message = "Имя не должно быть пустым")
    @NotNull(message = "Имя не должно быть пустым")
    private String firstName;
    @NotBlank(message = "Фамилия не должна быть пустым")
    @NotNull(message = "Фамилия не должна быть пустым")
    private String lastName;
    @NotBlank(message = "Номер телефона не должна быть пустым.")
    @NotNull(message = "Номер телефона не должна быть пустым.")
    @PhoneNumberValid(message = "Номер телефона не должна быть больше 13 цифр и должен начинаться с +996.")
    private String phoneNumber;
    @NotBlank(message = "Электронная почта не должна быть пустым.")
    @NotNull(message = "Электронная почта не должна быть пустым.")
    @Email(message = "Электронная почта должна содержать @ ")
    private String email;
    @NotBlank(message = "Group id should not be null")
    @NotNull(message = "Group id can't be empty")
    private Long groupId;
    private FormLearning formLearning;
    @NotBlank(message = "Имя ссылки не может быть пустым")
    @NotNull(message = "Имя ссылки не может быть пустым")
    private String link;
}
