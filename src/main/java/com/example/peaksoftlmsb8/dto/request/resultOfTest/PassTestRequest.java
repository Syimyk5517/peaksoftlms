package com.example.peaksoftlmsb8.dto.request.resultOfTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassTestRequest {
    private Long testId;
    private List<PassQuestionRequest> passQuestionRequest;
}
