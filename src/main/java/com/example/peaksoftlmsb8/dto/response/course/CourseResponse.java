package com.example.peaksoftlmsb8.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseResponse {
    private Long id;
    private String name;
    private String image;
    private String description;
    private LocalDate createdAt;
    private LocalDate finishDate;
}
