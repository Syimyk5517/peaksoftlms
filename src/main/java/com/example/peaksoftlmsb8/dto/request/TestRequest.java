package com.example.peaksoftlmsb8.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class TestRequest {
    private String testName;
    private List<QuestionRequest> questionRequests;
    private Long lessonId;

}
