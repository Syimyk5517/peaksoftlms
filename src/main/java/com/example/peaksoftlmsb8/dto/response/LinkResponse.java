package com.example.peaksoftlmsb8.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkResponse {
    private Long lessonId;
    private Map<String, String> link;


}

