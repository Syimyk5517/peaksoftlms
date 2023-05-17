package com.example.peaksoftlmsb8.dto.response;

import lombok.*;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@NoArgsConstructor
public class CourseResponse {
    private Long id;
    private String name;
    private String image;
    private String description;
    private LocalDate createdAt;
    private LocalDate finalDate;

    public CourseResponse(Long id, String name, String image, String description, LocalDate createdAt, LocalDate finalDate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
        this.finalDate = finalDate;
    }
}
