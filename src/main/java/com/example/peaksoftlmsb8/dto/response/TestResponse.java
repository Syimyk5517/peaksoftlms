package com.example.peaksoftlmsb8.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestResponse {
    private Long LessonId;
    private String lessonName;
    private Long testId;
    private String testName;
    private LocalDate dateTest;
    private List<QuestionResponse> questionResponses;
}
