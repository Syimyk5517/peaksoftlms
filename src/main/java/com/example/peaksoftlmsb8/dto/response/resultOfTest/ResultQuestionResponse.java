package com.example.peaksoftlmsb8.dto.response.resultOfTest;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ResultQuestionResponse {
    private Long questionId;
    private String questionName;
    private OptionType optionType;
    private int point;
    private List<Long> studentAnswers;
    private List<ResultOptionResponse> optionResponses;
}
