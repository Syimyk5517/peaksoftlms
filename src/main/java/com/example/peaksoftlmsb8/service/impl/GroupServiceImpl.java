package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.*;
import com.example.peaksoftlmsb8.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;

    @Override
    public SimpleResponse saveGroup(GroupRequest groupRequest) {
        if (groupRepository.existsGroupByName(groupRequest.getName())) {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).
                    message(String.format("Group with name :" + groupRequest.getName() + " already exists")).build();
        }
        Group group = new Group();
        group.setName(groupRequest.getName());
        group.setCreatedAt(LocalDate.now());
        group.setDescription(groupRequest.getDescription());
        group.setImage(groupRequest.getImage());
        group.setFinalDate(groupRequest.getFinalDate());
        groupRepository.save(group);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Group with name : " + groupRequest.getName() + " successfully saved").build();
    }

    @Override
    public GroupPaginationResponse getAllGroups(int size, int page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<GroupResponse> groupPage = groupRepository.getAllGroups(pageable);
        GroupPaginationResponse groupPaginationResponse = new GroupPaginationResponse();
        groupPaginationResponse.setGroupResponses(groupPage.getContent());
        groupPaginationResponse.setPageSize(groupPage.getNumber());
        groupPaginationResponse.setCurrentPage(groupPage.getSize());
        return groupPaginationResponse;
    }

    @Override
    public GroupResponse getGroupById(Long groupId) {
        return groupRepository.getGroupById(groupId).orElseThrow(() -> new NotFoundException(String.format("Group id:" + groupId + " not found")));
    }

    @Override
    public SimpleResponse updateGroup(Long groupId, GroupRequest groupRequest) {
        if (groupRepository.existsGroupByName(groupRequest.getName())) {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).
                    message(String.format("Group with name : " + groupRequest.getName() + " already exists")).build();
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new NotFoundException(String.format("Group with id: " + groupId + " not found")));
        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setImage(groupRequest.getImage());
        group.setFinalDate(groupRequest.getFinalDate());
        groupRepository.save(group);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    @Transactional
    public SimpleResponse deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new NotFoundException(String.format("Group with id: " + groupId + " not found")));
        groupRepository.delete(group);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted").build();
    }

    @Override
    public SimpleResponse assignGroupToCourse(Long groupId, Long courseId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group with id : " + groupId + " not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with id: " + courseId + " not found"));
        group.addCourse(course);
        groupRepository.save(group);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved!")
                .build();
    }
}
