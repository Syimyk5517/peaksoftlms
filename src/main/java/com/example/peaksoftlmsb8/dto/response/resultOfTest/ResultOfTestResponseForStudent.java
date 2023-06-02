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
public class ResultOfTestResponseForStudent {
    private Long resultOfTestId;
    private Long testId;
    private String testName;
    private int studentPoint;
    private List<ResultQuestionResponse> resultQuestionResponses;
}
