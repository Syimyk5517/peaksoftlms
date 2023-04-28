package com.example.peaksoftlmsb8.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresentationResponse {
    private Long id;
    private String name;
    private String description;
    private String formatPPT;
    private Long lessonId;
}
