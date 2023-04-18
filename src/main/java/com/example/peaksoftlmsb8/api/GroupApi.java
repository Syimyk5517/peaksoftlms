package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupApi {
    private final GroupService groupService;
    @PostMapping
    public SimpleResponse saveGroup(@RequestBody @Valid GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }
    @GetMapping("/{sort}")
    public GroupPaginationResponse getAllGroups(@RequestParam int size,
                                                @RequestParam int page,
                                                @PathVariable String sort,
                                                @RequestBody String word) {
        return groupService.getAllGroups(size, page, sort, word);
    }
    @GetMapping("/{groupId}")
    public GroupResponse getGroupById(@PathVariable Long groupId) {

        return groupService.getGroupById(groupId);
    }
    @PutMapping("/{groupId}")
    public SimpleResponse updateGroup(@PathVariable Long groupId, @RequestBody @Valid GroupRequest groupRequest) {
        return groupService.updateGroup(groupId, groupRequest);
    }
    @DeleteMapping("/{groupId}")
    public SimpleResponse deleteGroup(@PathVariable Long groupId) {
        return groupService.deleteGroup(groupId);
    }
}

