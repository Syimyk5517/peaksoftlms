package com.example.peaksoftlmsb8.dto.request;

import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String name;
    private String description;
    private String file;
    private LocalDate deadline;
}
