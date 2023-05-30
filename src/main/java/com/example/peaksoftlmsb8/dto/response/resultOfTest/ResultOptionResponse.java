package com.example.peaksoftlmsb8.dto.response.resultOfTest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultOptionResponse {
    private Long optionId;
    private String text;
    private Boolean isTrue;

}
