package com.example.peaksoftlmsb8.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponseForInstructor {
    private List<InstructorResponse> instructorResponses;
    private int currentPage;
    private int pageSize;
}
