package com.example.peaksoftlmsb8.dto.response.test;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestResponseForStudent implements TestResponse {
    private Long lessonId;
    private Long testId;
    private String testName;
    private List<QuestionResponseForStudent> questionResponses;
}
