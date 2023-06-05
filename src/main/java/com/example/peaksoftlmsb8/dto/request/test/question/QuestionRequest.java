package com.example.peaksoftlmsb8.dto.request.test.question;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.dto.request.test.option.OptionRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class QuestionRequest {
    @NotNull(message = "Question  name should not be null")
    @NotBlank(message = "Question name can't be empty!")
    private String questionName;
    private List<OptionRequest> optionRequests;
    @NotNull(message = "Option type should not be null")
    @NotBlank(message = "Option type can't be empty!")
    private OptionType optionType;
}
