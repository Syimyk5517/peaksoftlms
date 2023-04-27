package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresentationRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String formatPPT;
    private Long lessonId;
}
