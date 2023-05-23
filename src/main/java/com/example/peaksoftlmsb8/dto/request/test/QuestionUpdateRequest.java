package com.example.peaksoftlmsb8.dto.request.test;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.dto.request.test.OptionUpdateRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionUpdateRequest {
    private Long questionId;
    private String questionName;
    private OptionType optionType;
    private List<OptionUpdateRequest> optionRequests;
}
