package com.example.peaksoftlmsb8.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignRequest {
    @NotBlank(message = "Instructors ids should not be null")
    private List<Long> instructorIds;
    @NotBlank(message = "Course id should not be null")
    private Long courseId;
}
