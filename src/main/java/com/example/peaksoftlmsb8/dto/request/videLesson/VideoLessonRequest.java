package com.example.peaksoftlmsb8.dto.request.videLesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoLessonRequest {
    @NotBlank(message = "Название видео не может быть пустым!")
    @NotNull(message = "Название видео не может быть пустым!")
    private String name;
    @NotBlank(message = "Описание не может быть пустым!")
    @NotNull(message = "Описание не может быть пустым!")
    private String description;
    @NotBlank(message = "Ссылка на видео не может быть пустой!")
    @NotNull(message = "Ссылка на видео не может быть пустой")
    private String videoLink;
}
