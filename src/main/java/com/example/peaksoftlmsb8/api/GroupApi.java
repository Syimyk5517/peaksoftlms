package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Groups")
public class GroupApi {
    private final GroupService groupService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveGroup(@RequestBody @Valid GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public GroupPaginationResponse getAllGroups(@RequestParam int size,
                                                @RequestParam int page) {
        return groupService.getAllGroups(size, page);
    }

    @GetMapping("/getById")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public GroupResponse getGroupById(@RequestParam Long groupId) {
        return groupService.getGroupById(groupId);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateGroup(@RequestParam Long groupId, @RequestBody @Valid GroupRequest groupRequest) {
        return groupService.updateGroup(groupId, groupRequest);
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteGroup(@RequestParam Long groupId) {
        return groupService.deleteGroup(groupId);
    }


    @PostMapping("/assign")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse assignGroupToCourse(@RequestParam Long groupId, @RequestParam Long courseId) {
        return groupService.assignGroupToCourse(groupId, courseId);
    }
}

