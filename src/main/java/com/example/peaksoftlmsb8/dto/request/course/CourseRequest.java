package com.example.peaksoftlmsb8.dto.request.course;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    @NotNull(message = "Имя курса не может быть пустым!")
    @NotBlank(message = "Имя курса не может быть пустым!")
    private String name;
    @NotNull(message = "Изображение не может быть пустым!")
    @NotBlank(message = "Изображение не может быть пустым!")
    private String image;
    @NotNull(message = "Идентификатор курса не должен быть пустым.")
    @NotBlank(message = "Идентификатор курса не должен быть пустым.")
    private String description;
    @Future(message = "Дата окончания должен быть будущей датой")
    private LocalDate finishDate;
}
