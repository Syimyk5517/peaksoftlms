package com.example.peaksoftlmsb8.dto.request.student;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.poiji.annotation.ExcelCellName;
import lombok.Data;

@Data
public class StudentExcelRequest {
    @ExcelCellName("First name")
    private String firstNAme;
    @ExcelCellName("Last name")
    private String lastName;
    @ExcelCellName("Phone number")
    private String phoneNumber;
    @ExcelCellName("Form learning")
    private FormLearning formLearning;
    @ExcelCellName("email")
    private String email;
}
