package com.example.peaksoftlmsb8.dto.request.group;

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
public class GroupRequest {
    @NotNull(message = "Имя група не должно быть пустым")
    @NotBlank(message = "Имя група не должно быть пустым!")
    private String name;
    @NotBlank(message = "Описание не должно быть пустым.")
    @NotNull(message = "Описание не должно быть пустым.")
    private String description;
    @NotBlank(message = "Изображение не должно быть пустым.")
    @NotNull(message = "Изображение не должно быть пустым.")
    private String image;
    @Future(message = "Дата окончания должна быть будущей датой")
    private LocalDate finishDate;
}
