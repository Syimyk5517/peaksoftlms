package com.example.peaksoftlmsb8.dto.request.test;

import com.example.peaksoftlmsb8.dto.request.test.question.QuestionRequest;
import com.example.peaksoftlmsb8.dto.request.test.question.QuestionUpdateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class TestUpdateRequest {

    private Long testId;
    @NotNull(message = "Имя теста не должно быть пустым")
    @NotBlank(message = "Имя теста не должно быть пустым")
    private String testName;
    private Long lessonId;
    private List<QuestionUpdateRequest> questionRequests;
    private List<QuestionRequest> questionRequestList;
    private List<Long> deleteQuestionsIds;
    private List<Long> deleteOptionsIds;

}
