package com.example.peaksoftlmsb8.dto.request.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequest {
    @NotNull(message = "Name should not be null")
    @NotBlank(message = "Name can't be empty!")
    private String name;
    @NotNull(message = "Course id should not be null")
    @NotBlank(message = "Course id can't be empty!")
    private Long courseId;
}
