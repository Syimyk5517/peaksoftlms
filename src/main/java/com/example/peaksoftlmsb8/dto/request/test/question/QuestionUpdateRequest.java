package com.example.peaksoftlmsb8.dto.request.test.question;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.dto.request.test.option.OptionUpdateRequest;
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
