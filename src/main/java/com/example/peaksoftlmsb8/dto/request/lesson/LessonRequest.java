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
    @NotNull(message = "Имя урока не может быть пустым!")
    @NotBlank(message = "Имя урока не может быть пустым!")
    private String name;
    private Long courseId;
}
