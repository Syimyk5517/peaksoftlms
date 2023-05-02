package com.example.peaksoftlmsb8.dto.response.test;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestResponse {
    private Long lessonId;
    private String lessonName;
    private Long testId;
    private String testName;
    private LocalDate dateTest;
    private List<QuestionResponse> questionResponses;
}
