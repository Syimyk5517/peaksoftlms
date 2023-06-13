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
    @NotNull(message = "Название вопроса не должно быть пустым")
    @NotBlank(message = "Название вопроса не должно быть пустым")
    private String questionName;
    private List<OptionRequest> optionRequests;

    private OptionType optionType;
}
