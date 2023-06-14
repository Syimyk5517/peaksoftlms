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
    @NotNull(message = "Балы не должна быть нулевой.")
    private Integer point;
    private Long taskAnswerId;
}
