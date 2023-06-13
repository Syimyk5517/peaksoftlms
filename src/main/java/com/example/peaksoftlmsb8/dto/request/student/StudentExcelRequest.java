package com.example.peaksoftlmsb8.dto.request.student;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.validation.email.EmailValid;
import com.example.peaksoftlmsb8.validation.phoneNumber.PhoneNumberValid;
import com.poiji.annotation.ExcelCellName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentExcelRequest {
    @ExcelCellName("First name")
    @NotBlank(message = "Имя не должно быть пустым")
    @NotNull(message = "Имя не должно быть пустым")
    private String firstNAme;
    @ExcelCellName("Last name")
    @NotBlank(message = "Фамилия не должна быть пустым")
    @NotNull(message = "Фамилия не должна быть пустым")
    private String lastName;
    @ExcelCellName("Phone number")
    @NotBlank(message = "Номер телефона не должна быть пустым.")
    @NotNull(message = "Номер телефона не должна быть пустым.")
    @PhoneNumberValid(message = "Номер телефона не должна быть больше 13 цифр и должен начинаться с +996.")
    private String phoneNumber;
    @ExcelCellName("Form learning")
    private FormLearning formLearning;
    @ExcelCellName("email")
    @NotBlank(message = "Электронная почта не должна быть пустым.")
    @NotNull(message = "Электронная почта не должна быть пустым.")
    @EmailValid(message = "Электронная почта должна содержать @ ")
    private String email;
}
