package com.example.peaksoftlmsb8.dto.response.test;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionResponseForStudent {
    private Long questionId;
    private String questionName;
    private OptionType optionType;
    private List<OptionResponseForStudent> optionResponses;
}
