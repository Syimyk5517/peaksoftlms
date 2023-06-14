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
    @NotNull(message = "Имя теста не должно быть пустым")
    @NotBlank(message = "Имя теста не должно быть пустым")
    private String testName;
    private Long lessonId;
    private List<QuestionRequest> questionRequests;
}
