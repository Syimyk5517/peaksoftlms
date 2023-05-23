package com.example.peaksoftlmsb8.dto.response.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentPaginationResponse {
    private List<StudentResponse> studentResponses;
    private int currentPage;
    private int pageSize;
}
