package com.example.peaksoftlmsb8.dto.request.test.option;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionUpdateRequest {
    private Long optionId;
    @NotNull(message = "Текстовое поля не может быть пустым!")
    @NotBlank(message = "Текстовое поля не может быть пустым!")
    private String text;

    private Boolean isTrue;

}
