package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.dto.request.group.GroupRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.GroupRepository;
import com.example.peaksoftlmsb8.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import com.example.peaksoftlmsb8.dto.request.group.GroupUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

public class GroupServiceImplTest {
    @Mock
    private GroupRepository groupRepository;
    @InjectMocks
    private GroupServiceImpl groupService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveGroup() {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setName("Group 1");
        groupRequest.setDescription("Description");
        groupRequest.setImage("Image URL");
        groupRequest.setFinishDate(LocalDate.now().plusMonths(3));
        Mockito.when(groupRepository.existsGroupByName(groupRequest.getName()))
                .thenReturn(false);
        SimpleResponse response = groupService.saveGroup(groupRequest);
        Assertions.assertEquals(HttpStatus.OK, response.getHttpStatus());
        Assertions.assertEquals("Group with name: Group 1 successfully saved", response.getMessage());
        Mockito.verify(groupRepository).existsGroupByName(groupRequest.getName());
        Mockito.verify(groupRepository).save(Mockito.any(Group.class));
    }

    @Test
    public void testUpdateGroup_GroupExists() {
        long groupId = 25;
        GroupUpdateRequest updateRequest = new GroupUpdateRequest();
        updateRequest.setGroupId(groupId);
        updateRequest.setName("Updated Group");
        updateRequest.setDescription("Updated description");
        updateRequest.setImage("updated-image.jpg");
        updateRequest.setFinishDate(LocalDate.of(2023, 12, 12));
        Group existingGroup = new Group();
        existingGroup.setId(groupId);
        existingGroup.setName("Original Group");
        existingGroup.setDescription("Original description");
        existingGroup.setImage("original-image.jpg");
        existingGroup.setFinishDate(LocalDate.of(2023, 12, 12));
        Mockito.when(groupRepository.findById(groupId))
                .thenReturn(Optional.of(existingGroup));
        SimpleResponse response = groupService.updateGroup(updateRequest);
        Assertions.assertEquals(HttpStatus.OK, response.getHttpStatus());
        Assertions.assertEquals("Successfully updated", response.getMessage());
        Assertions.assertEquals(updateRequest.getName(), existingGroup.getName());
        Assertions.assertEquals(updateRequest.getDescription(), existingGroup.getDescription());
        Assertions.assertEquals(updateRequest.getImage(), existingGroup.getImage());
        Assertions.assertEquals(updateRequest.getFinishDate(), existingGroup.getFinishDate());
        Mockito.verify(groupRepository).findById(groupId);
        Mockito.verify(groupRepository).save(existingGroup);
    }
}







