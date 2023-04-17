package com.example.peaksoftlmsb8.dto.response;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.enums.FormLearning;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private FormLearning formLearning;
    private Boolean isBlocked;
    private Long rating;
    private User user;
    private Group group;
}
