package com.example.peaksoftlmsb8.dto.request.test;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class QuestionRequest {
    private String questionName;
    private OptionType optionType;
    private List<OptionRequest> optionRequests;
}
