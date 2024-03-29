package com.example.peaksoftlmsb8.dto.response.test;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionResponse {
    private Long questionId;
    private String questionName;
    private OptionType optionType;
    private List<OptionResponse> optionResponses;

    public void addOption(OptionResponse optionResponse){
        if (optionResponses == null){
            optionResponses = new ArrayList<>();
        }
        optionResponses.add(optionResponse);
    }
}
