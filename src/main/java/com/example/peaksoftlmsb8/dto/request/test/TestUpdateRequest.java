package com.example.peaksoftlmsb8.dto.request.test;

import com.example.peaksoftlmsb8.dto.request.test.question.QuestionUpdateRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class TestUpdateRequest {
    private Long testId;
    private String testName;
    private Long lessonId;
    private List<QuestionUpdateRequest> questionRequests;
    private List<QuestionRequest> questionRequestList;
    private List<Long> deleteQuestionsIds;
    private List<Long> deleteOptionsIds;
    private String testName;
    private List<QuestionUpdateRequest> questionRequests;
    private Long lessonId;

}
