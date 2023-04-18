package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;


public interface GroupService {
    SimpleResponse saveGroup(GroupRequest groupRequest);

    GroupPaginationResponse getAllGroups(int size, int page, String word, String sort);

    GroupResponse getGroupById(Long groupId);

    SimpleResponse updateGroup(Long groupId, GroupRequest groupRequest);

    SimpleResponse deleteGroup(Long groupId);
}
