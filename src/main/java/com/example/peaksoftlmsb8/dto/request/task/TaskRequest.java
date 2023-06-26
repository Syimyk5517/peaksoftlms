package com.example.peaksoftlmsb8.dto.request.task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotNull(message = "Имя задания не может быть пустым!")
    @NotBlank(message = "Имя задания не может быть пустым!")
    private String name;
    @NotBlank(message = "Описание не должно быть пустым.")
    @NotNull(message = "Описание не должно быть пустым.")
    private String description;
    @NotBlank(message = "Файл не должно быть пустым.")
    @NotNull(message = "Файл не должно быть пустым.")
    private String file;
    @Future(message = "Крайний срок должен быть будущей датой")
    private LocalDate deadline;
}
