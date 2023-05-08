package com.example.peaksoftlmsb8.dto.request.test;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OptionUpdateRequest {
    private Long optionId;
    private String text;
    private Boolean isTrue;


}
