package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.request.GroupUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;


public interface GroupService {
    SimpleResponse saveGroup(GroupRequest groupRequest);

    GroupPaginationResponse getAllGroups(int size, int page);

    GroupResponse getGroupById(Long groupId);

    SimpleResponse updateGroup(GroupUpdateRequest groupUpdateRequest);

    SimpleResponse deleteGroup(Long groupId);

    SimpleResponse assignGroupToCourse(Long groupId, Long courseId);
}
