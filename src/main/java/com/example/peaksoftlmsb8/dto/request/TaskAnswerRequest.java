package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskAnswerRequest {
    @NotNull(message = "Task value should not be null")
    @NotBlank(message = "Task value can't be empty!")
    private String taskValue;
}
