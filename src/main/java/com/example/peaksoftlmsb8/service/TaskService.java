package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

public interface TaskService {
    SimpleResponse getAllTasks(int size, int page,String search,String sort);
    SimpleResponse getByIdTask(Long taskId);
    SimpleResponse saveTask(TaskRequest taskRequest);
    SimpleResponse updateTask(Long taskId, TaskRequest taskRequest);
    SimpleResponse deleteTaskById(Long taskId);
}
