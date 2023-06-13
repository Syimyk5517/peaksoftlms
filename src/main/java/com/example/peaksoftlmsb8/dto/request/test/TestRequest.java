package com.example.peaksoftlmsb8.dto.request.test;

import com.example.peaksoftlmsb8.dto.request.test.question.QuestionRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestRequest {
    @NotNull(message = "Test name should not be null")
    @NotBlank(message = "Test name can't be empty!")
    private String testName;
    private Long lessonId;
    private List<QuestionRequest> questionRequests;
}
