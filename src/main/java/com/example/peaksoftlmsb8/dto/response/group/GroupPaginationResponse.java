package com.example.peaksoftlmsb8.dto.response.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupPaginationResponse {
    private List<GroupResponse> groupResponses;
    private int currentPage;
    private int pageSize;
}

