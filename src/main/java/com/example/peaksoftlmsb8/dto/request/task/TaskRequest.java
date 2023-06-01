package com.example.peaksoftlmsb8.dto.request.task;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String file;

    private LocalDate deadline;
    private Long lessonId;
}
