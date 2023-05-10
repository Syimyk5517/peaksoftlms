package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Name should not be null")
    @NotBlank(message = "Name can't be empty!")
    private String name;
    @NotBlank(message = "Description should not be null")
    @NotNull(message = "Description can't be empty")
    private String description;
    @NotBlank(message = " Image should not be null")
    @NotNull(message = "Image can't be empty")
    private String image;
    @Future(message = "Finish date should be future date")
    private LocalDate finishDate;
}
