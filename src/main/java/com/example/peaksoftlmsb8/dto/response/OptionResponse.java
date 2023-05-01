package com.example.peaksoftlmsb8.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionResponse {
    private Long optionId;
    private String text;
    private Boolean isTrue;
}
