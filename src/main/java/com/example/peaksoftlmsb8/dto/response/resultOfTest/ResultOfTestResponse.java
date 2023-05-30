package com.example.peaksoftlmsb8.dto.response.resultOfTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultOfTestResponse {
    private Long testId;
    private String testName;
    private int studentPoint;
    private int allPoints;
    private List<ResultQuestionResponse> resultQuestionResponses;
}
