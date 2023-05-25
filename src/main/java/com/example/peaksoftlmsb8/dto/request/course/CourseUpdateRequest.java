package com.example.peaksoftlmsb8.dto.request.course;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    private Long courseId;
    private String name;
    private String image;
    private String description;
    private LocalDate createdAt;
    private LocalDate finishDate;
}
