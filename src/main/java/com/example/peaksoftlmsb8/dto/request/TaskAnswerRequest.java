package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskAnswerRequest {
    @NotNull(message = "Значение задачи не должно быть пустым")
    @NotBlank(message = "Значение задачи не должно быть пустым")
    private String taskValue;
}
