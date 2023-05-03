package com.example.peaksoftlmsb8.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonPaginationResponse {
    private List<LessonResponse> lessonResponses;
    private int currentPage;
    private int pageSize;
}
