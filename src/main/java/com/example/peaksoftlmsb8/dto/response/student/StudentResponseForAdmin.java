package com.example.peaksoftlmsb8.dto.response.student;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseForAdmin {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private FormLearning formLearning;
    private String groupName;
    private String groupId;
}
