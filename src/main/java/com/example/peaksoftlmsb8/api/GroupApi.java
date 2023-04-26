package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupApi {
    private final GroupService groupService;

    @PostMapping
    public SimpleResponse saveGroup(@RequestBody @Valid GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }

    @GetMapping("/pagination")
    public GroupPaginationResponse getAllGroups(@RequestParam int size,
                                                @RequestParam int page,
                                                @RequestParam String sort,
                                                @RequestBody String word) {
        return groupService.getAllGroups(size, page, sort, word);
    }

    @GetMapping("/getById")
    public GroupResponse getGroupById(@RequestParam Long groupId) {

        return groupService.getGroupById(groupId);
    }

    @PutMapping()
    public SimpleResponse updateGroup(@RequestParam Long groupId, @RequestBody @Valid GroupRequest groupRequest) {
        return groupService.updateGroup(groupId, groupRequest);
    }

    @DeleteMapping()
    public SimpleResponse deleteGroup(@RequestParam Long groupId) {
        return groupService.deleteGroup(groupId);
    }

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse assignGroupToCourse(@RequestParam Long groupId, @RequestParam Long courseId) {
        return groupService.assignGroupToCourse(groupId, courseId);
    }
}

