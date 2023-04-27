package com.example.peaksoftlmsb8.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionRequest {
    private String text;
    private Boolean isTrue;

}
