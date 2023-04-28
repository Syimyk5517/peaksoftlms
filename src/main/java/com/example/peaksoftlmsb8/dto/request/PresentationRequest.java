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
public class PresentationRequest {
    @NotNull(message = "Name should not be null")
    @NotBlank(message = "Name can't be empty!")
    private String name;
    @NotBlank(message = "Description can't be empty!")
    @NotNull(message = "Description should not be null")
    private String description;
    @NotBlank(message = "FormatPPT can't be empty!")
    @NotNull(message = "FormatPPT should not be null")
    private String formatPPT;
    private Long lessonId;
}
