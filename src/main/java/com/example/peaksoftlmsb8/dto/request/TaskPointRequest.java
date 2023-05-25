package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskPointRequest {
    @Positive
    private Integer point;
    private Long taskAnswerId;
}
