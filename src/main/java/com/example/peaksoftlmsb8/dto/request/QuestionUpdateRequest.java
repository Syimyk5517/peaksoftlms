package com.example.peaksoftlmsb8.dto.request;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class QuestionUpdateRequest {
    private String questionName;
    private Long questionId;
    private List<OptionUpdateRequest> optionRequests;
    private OptionType optionType;
}
