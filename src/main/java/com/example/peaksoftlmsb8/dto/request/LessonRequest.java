package com.example.peaksoftlmsb8.dto.request;

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
    private Long courseId;
}