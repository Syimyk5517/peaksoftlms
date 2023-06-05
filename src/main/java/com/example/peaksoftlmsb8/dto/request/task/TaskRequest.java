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
    @NotNull(message = "Name should not be null")
    @NotBlank(message = "Name can't be empty!")
    private String name;
    @NotNull(message = "Description  should not be null")
    @NotBlank(message = "Description can't be empty!")
    private String description;
    @NotNull(message = "File should not be null")
    @NotBlank(message = "File can't be empty!")
    private String file;
    @Future(message = "deadline should be future date")
    private LocalDate deadline;
    private Long lessonId;
}
