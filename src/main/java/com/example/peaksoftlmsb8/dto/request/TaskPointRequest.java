package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Point should not be null")
    private Integer point;
    private Long taskAnswerId;
}
