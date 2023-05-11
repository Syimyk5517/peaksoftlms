package com.example.peaksoftlmsb8.dto.response;

import com.example.peaksoftlmsb8.db.enums.TaskAnswerFormat;
import com.example.peaksoftlmsb8.db.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAnswerResponse {
    private Long id;
    private TaskAnswerFormat taskAnswerFormat;
    private String taskValue;
    private TaskStatus taskStatus;
}
