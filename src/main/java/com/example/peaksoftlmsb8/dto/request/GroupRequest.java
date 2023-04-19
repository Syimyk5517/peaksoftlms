package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
    @NotBlank(message = "Name should not be null")
    private String name;
    @NotBlank(message = "Description should not be null")
    private String description;
    @NotBlank(message = " Image should not be null")
    private String image;
    private LocalDate finalDate;
}
