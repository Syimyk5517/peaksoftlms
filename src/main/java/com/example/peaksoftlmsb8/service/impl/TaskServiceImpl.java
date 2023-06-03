package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Task;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.task.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.task.TaskResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.TaskRepository;
import com.example.peaksoftlmsb8.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;


    @Override
    public TaskResponse getByTaskId(Long taskId) {
        return taskRepository.findByIdTask(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id:" + taskId + " not found")));
    }

    @Override
    public List<TaskResponse> getAllTaskByLessonId(Long lessonId) {
        return taskRepository.findAllTaskByLessonId(lessonId);
    }

    @Override
    public SimpleResponse saveTask(TaskRequest taskRequest) {
        Lesson lesson = lessonRepository.findById(taskRequest.getLessonId())
                .orElseThrow(() -> new NotFoundException((String.format("Lesson with id: " + taskRequest.getLessonId() + " not found"))));
        if (taskRepository.existsByName(taskRequest.getName())) {
            throw new AlReadyExistException("Task with name :" + taskRequest.getName() + " already exists");
        }
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setFile(taskRequest.getFile());
        task.setDeadline(taskRequest.getDeadline());
        task.setLesson(lesson);
        taskRepository.save(task);


        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved!")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse updateTask(Long taskId, TaskRequest newTaskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id:" + taskId + "not found"));
        if (taskRepository.existsByName(newTaskRequest.getName())) {
            throw new AlReadyExistException("Task with name :" + newTaskRequest.getName() + " already exists");
        }
        task.setName(newTaskRequest.getName());
        task.setDescription(newTaskRequest.getDescription());
        task.setFile(newTaskRequest.getFile());
        task.setDeadline(newTaskRequest.getDeadline());
        taskRepository.save(task);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new NotFoundException(String.format("Presentation with id: " + taskId + " not found")));
        taskRepository.delete(task);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted!")
                .build();
    }


}
