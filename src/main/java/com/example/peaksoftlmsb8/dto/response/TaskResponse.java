package com.example.peaksoftlmsb8.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private String file;
    private LocalDate deadline;
    private Long lessonId;
}
