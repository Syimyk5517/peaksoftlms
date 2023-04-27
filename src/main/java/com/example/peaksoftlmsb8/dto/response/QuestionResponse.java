package com.example.peaksoftlmsb8.dto.response;

import com.example.peaksoftlmsb8.db.entity.Option;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
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
