package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.task.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.task.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse getByTaskId(Long taskId);

    List<TaskResponse> getAllTaskByLessonId(Long lessonId);

    SimpleResponse saveTask(Long lessonId,TaskRequest taskRequest);

    SimpleResponse updateTask(Long taskId, TaskRequest newTaskRequest);

    SimpleResponse deleteTaskById(Long taskId);
}
