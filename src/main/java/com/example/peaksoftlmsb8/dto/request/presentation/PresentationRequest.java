package com.example.peaksoftlmsb8.dto.request.presentation;

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
public class PresentationRequest {
    @NotNull(message = "Имя презентация  не должно быть пустым")
    @NotBlank(message = "Имя презентация  не должно быть пустым")
    private String name;
    @NotBlank(message = "Описание не должно быть пустым.")
    @NotNull(message = "Описание не должно быть пустым.")
    private String description;
    @NotBlank(message = "Необходимо указать FormatPPT!")
    @NotNull(message = "Необходимо указать FormatPPT!")
    private String formatPPT;

    private Long lessonId;
}
