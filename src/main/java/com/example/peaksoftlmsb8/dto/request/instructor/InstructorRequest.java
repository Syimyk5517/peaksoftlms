package com.example.peaksoftlmsb8.dto.request.instructor;

import com.example.peaksoftlmsb8.validation.email.EmailValid;
import com.example.peaksoftlmsb8.validation.phoneNumber.PhoneNumberValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRequest {
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
    @EmailValid(message = "Электронная почта должна содержать @ ")
    private String email;
    @NotBlank(message = "Специальное значение не должно быть пустым.")
    @NotNull(message = "Необходимо указать специальное значение.")
    private String special;
    @NotBlank(message = "Имя ссылки не может быть пустым")
    @NotNull(message = "Имя ссылки не может быть пустым")
    private String link;
}
