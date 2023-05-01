package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TaskResponse;
import com.example.peaksoftlmsb8.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskApi {
    private final TaskService taskService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTUCTOR')")
    public SimpleResponse saveTask(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.saveTask(taskRequest);
    }

    @GetMapping()
    public List<TaskResponse> getTaskByLessonId(@RequestParam Long lessonId) {
        return taskService.getAllTaskByLessonId(lessonId);
    }

    @GetMapping("/getById")
    public TaskResponse getTaskById(@RequestParam Long taskId) {
        return taskService.getByTaskId(taskId);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse updateTask(@RequestParam Long taskId, @RequestBody @Valid TaskRequest taskRequest) {
        return taskService.updateTask(taskId, taskRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse deleteTask(@RequestParam Long taskId) {
        return taskService.deleteTaskById(taskId);
    }
}