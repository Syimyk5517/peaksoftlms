package com.example.peaksoftlmsb8.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionResponse {
    private Long optionId;
    private String text;
    private Boolean isTrue;
}
