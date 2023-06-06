package com.example.peaksoftlmsb8.dto.request.test.option;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionUpdateRequest {
    private Long optionId;
    @NotNull(message = "Text should not be null")
    @NotBlank(message = "Text name can't be empty!")
    private String text;
    @NotNull(message = "Is true  should not be null")
    private Boolean isTrue;

}
