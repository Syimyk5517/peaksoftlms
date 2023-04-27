package com.example.peaksoftlmsb8.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class TestResponse {
    private Long LessonId;
    private String lessonName;
    private Long testId;
    private String testName;
    private LocalDate dateTest;
    private List<QuestionResponse> questionResponses;
}
