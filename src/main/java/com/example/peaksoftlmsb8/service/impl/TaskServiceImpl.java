package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.dto.request.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.TaskRepository;
import com.example.peaksoftlmsb8.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public SimpleResponse getAllTasks(int size, int page, String search, String sort) {

        return null;
    }

    @Override
    public SimpleResponse getByIdTask(Long taskId) {
        return null;
    }

    @Override
    public SimpleResponse saveTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public SimpleResponse updateTask(Long taskId, TaskRequest taskRequest) {
        return null;
    }

    @Override
    public SimpleResponse deleteTaskById(Long taskId) {
        return null;
    }
}
