package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.request.GroupUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Save a new group", description = "Save a new group with the provided details")
    public SimpleResponse saveGroup(@RequestBody @Valid GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all groups", description = "Retrieve a paginated list of all groups")
    public GroupPaginationResponse getAllGroups(@RequestParam int size,
                                                @RequestParam int page) {
        return groupService.getAllGroups(size, page);
    }
  
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get group by ID", description = "Get the group details for the provided ID")
    public GroupResponse getGroupById(@RequestParam Long groupId) {
        return groupService.getGroupById(groupId);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a group", description = "Update a group with the provided groupId using the information provided in the request body")
    public SimpleResponse updateGroup( @RequestBody @Valid GroupUpdateRequest groupUpdateRequest) {
        return groupService.updateGroup(groupUpdateRequest);
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a group by ID", description = "Deletes a group with the specified ID. This action requires admin authorization")
    public SimpleResponse deleteGroup(@RequestParam Long groupId) {
        return groupService.deleteGroup(groupId);
    }

    @PostMapping("/assign")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Assign group to course", description = "Assigns a group to a course by their respective IDs")
    public SimpleResponse assignGroupToCourse(@RequestParam Long groupId, @RequestParam Long courseId) {
        return groupService.assignGroupToCourse(groupId, courseId);
    }
}

