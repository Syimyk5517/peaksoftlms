package com.example.peaksoftlmsb8.dto.request;

import com.example.peaksoftlmsb8.db.enums.TaskAnswerFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskAnswerRequest {
    private TaskAnswerFormat taskAnswerFormat;
    private String taskValue;
}
