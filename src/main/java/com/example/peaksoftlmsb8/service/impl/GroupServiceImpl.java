package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.CourseRequest;
import com.example.peaksoftlmsb8.dto.request.GroupRequest;
import com.example.peaksoftlmsb8.dto.response.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.CourseRepository;
import com.example.peaksoftlmsb8.repository.GroupRepository;
import com.example.peaksoftlmsb8.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;

    @Override
    public SimpleResponse saveGroup(GroupRequest groupRequest) {
        if (groupRepository.existsGroupByName(groupRequest.getName())) {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).
                    message(String.format("Group with name :%s already exists", groupRequest.getName())).build();
        }
        Group group = new Group();
        group.setName(groupRequest.getName());
        group.setCreatedAt(LocalDate.now());
        group.setDescription(groupRequest.getDescription());
        group.setImage(groupRequest.getImage());
        group.setFinalDate(groupRequest.getFinalDate());
        groupRepository.save(group);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Group with name" + groupRequest.getName() + "successfully saved").build();
    }

    @Override
    public GroupPaginationResponse getAllGroups(int size, int page, String word, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<GroupResponse> groupPage = groupRepository.getAllGroups(pageable, word);
        List<GroupResponse> groupResponseList = new ArrayList<>(groupPage.getContent().stream()
                .map(g -> new GroupResponse(
                        g.getId(),
                        g.getName(),
                        g.getDescription(),
                        g.getImage(),
                        g.getFinalDate()
                )).toList());
        GroupPaginationResponse groupPaginationResponse = new GroupPaginationResponse();
        groupPaginationResponse.setGroupResponses(groupResponseList);
        groupPaginationResponse.setPageSize(groupPage.getNumber());
        groupPaginationResponse.setCurrentPage(groupPage.getSize());
        return groupPaginationResponse;
    }

    @Override
    public GroupResponse getGroupById(Long groupId) {
        return groupRepository.getGroupById(groupId).orElseThrow(() -> new NotFoundException(String.format("Group id:" + groupId + " not found")));
    }

    @Override
    @Transactional
    public SimpleResponse updateGroup(Long groupId, GroupRequest groupRequest) {
        if (groupRepository.existsGroupByName(groupRequest.getName())) {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).
                    message(String.format("Group with name :%s already exists", groupRequest.getName())).build();
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new NotFoundException(String.format("Group with id:" + groupId + "not found")));
        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setImage(groupRequest.getImage());
        group.setFinalDate(groupRequest.getFinalDate());
        groupRepository.save(group);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new NotFoundException(String.format("Group with id:" + groupId + "not found")));
        groupRepository.delete(group);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted").build();
    }

    @Override
    public SimpleResponse assignGroupToCourse(Long groupId, Long courseId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group with id:" + groupId + "not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with id:" + courseId + "not found"));
        group.AddCourse(course);
        groupRepository.save(group);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved!")
                .build();
    }
}
