package com.example.peaksoftlmsb8.dto.response.instructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponseForInstructor {
    private List<InstructorResponse> instructorResponses;
    private int currentPage;
    private int pageSize;
}
