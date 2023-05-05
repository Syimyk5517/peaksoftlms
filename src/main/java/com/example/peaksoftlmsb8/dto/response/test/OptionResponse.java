package com.example.peaksoftlmsb8.dto.response.test;

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
