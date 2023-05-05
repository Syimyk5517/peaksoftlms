package com.example.peaksoftlmsb8.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class TestUpdateRequest {
    private String testName;
    private List<QuestionUpdateRequest> questionRequests;
    private Long lessonId;
}
