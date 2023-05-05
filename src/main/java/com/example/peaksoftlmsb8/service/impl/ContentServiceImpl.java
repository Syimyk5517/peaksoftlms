package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Content;
import com.example.peaksoftlmsb8.db.entity.Task;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.ContentRequest;
import com.example.peaksoftlmsb8.dto.response.ContentResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.ContentRepository;
import com.example.peaksoftlmsb8.repository.TaskRepository;
import com.example.peaksoftlmsb8.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final TaskRepository taskRepository;

    @Override
    public SimpleResponse create(Long taskId, ContentRequest contentRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException("Task with ID: " + taskId + " is not found !"));
        Content content = new Content();
        content.setContentName(contentRequest.getContentName());
        content.setContentFormat(contentRequest.getContentFormat());
        content.setContentValue(contentRequest.getContentValue());
        content.setTask(task);
        task.getContents().add(content);
        taskRepository.save(task);
        contentRepository.save(content);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Content with Name: " + contentRequest.getContentName() + " is successfully saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long contentId, ContentRequest contentRequest) {
        Content content = contentRepository.findById(contentId).orElseThrow(
                () -> new NotFoundException("Content with ID: " + contentId + " is not found !"));
        if (!contentRequest.getContentName().equals(content.getContentName())) {
            content.setContentName(contentRequest.getContentName());
        }
        if (!contentRequest.getContentFormat().equals(content.getContentFormat())) {
            content.setContentFormat(contentRequest.getContentFormat());
        }
        if (!contentRequest.getContentValue().equals(content.getContentValue())) {
            content.setContentValue(contentRequest.getContentValue());
        }
        contentRepository.save(content);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Content with Name: " + contentRequest.getContentName() + " is successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long contentId) {
        if (!contentRepository.existsById(contentId)) {
            throw new BadRequestException("Content with ID: " + contentId + " is not found!");
        }
        contentRepository.deleteById(contentId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Content with ID: " + contentId + " is successfully deleted!")
                .build();
    }

    @Override
    public List<ContentResponse> findContentsByTaskId(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new BadRequestException("Task with ID: " + taskId + " is not found!");
        }
        return contentRepository.findAllByTaskId(taskId);
    }
}
