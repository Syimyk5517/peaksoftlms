package com.example.peaksoftlmsb8.dto.request;

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
    private List<Long> instructorIds;
    private Long courseId;
}
