package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.group.GroupRequest;
import com.example.peaksoftlmsb8.dto.request.group.GroupUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.group.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.group.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;


public interface GroupService {
    SimpleResponse saveGroup(GroupRequest groupRequest);

    GroupPaginationResponse getAllGroups(int size, int page);

    GroupResponse getGroupById(Long groupId);

    SimpleResponse updateGroup(GroupUpdateRequest groupUpdateRequest);

    SimpleResponse deleteGroup(Long groupId);

    SimpleResponse assignGroupToCourse(Long groupId, Long courseId);
}
