package com.example.peaksoftlmsb8.dto.request.test;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class TestRequest {
    private String testName;

    private Long lessonId;
    private List<QuestionRequest> questionRequests;
    private List<QuestionRequest> questionRequests;
    private Long lessonId;

}
