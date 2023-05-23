package com.example.peaksoftlmsb8.dto.response.videLesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoLessonResponse {
    private String name;
    private String description;
    private String videoLink;
}
