package com.example.peaksoftlmsb8.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkRequest {
    private String displayText;
    private String link;
    private Long lessonId;


}
