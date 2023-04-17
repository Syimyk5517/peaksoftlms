package com.example.peaksoftlmsb8.dto.request;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    private FormLearning formLearning;
    private Boolean isBlocked;
    private Long userId;
    private Long groupId;
}
