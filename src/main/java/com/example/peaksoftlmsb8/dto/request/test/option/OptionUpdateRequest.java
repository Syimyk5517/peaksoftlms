package com.example.peaksoftlmsb8.dto.request.test.option;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionUpdateRequest {
    private Long optionId;
    private String text;
    private Boolean isTrue;

}
