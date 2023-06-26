package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.task.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.task.TaskResponse;
import com.example.peaksoftlmsb8.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks")
@PostAuthorize("hasAnyAuthority('STUDENT,INSTRUCTOR')")
public class TaskApi {
    private final TaskService taskService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR,STUDENT')")
    @Operation(summary = "This method can save Tasks", description = "You can save Tasks in Database. Access to this method : ADMIN and INSTRUCTOR")
    public SimpleResponse saveTask(@RequestParam Long lessonId,@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.saveTask(lessonId,taskRequest);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR,STUDENT')")
    @GetMapping("/getLessonById")
    @Operation(summary = "This method can get Task by Lesson id", description = "You can get Task with sort desk")
    public List<TaskResponse> getTaskByLessonId(@RequestParam Long lessonId) {
        return taskService.getAllTaskByLessonId(lessonId);
    }

    @GetMapping("/getById")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR,STUDENT')")
    @Operation(summary = "This method can get Task with id", description = "You can get Task with id")
    public TaskResponse getTaskById(@RequestParam Long taskId) {
        return taskService.getByTaskId(taskId);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR,STUDENT')")
    @Operation(summary = "This method can update Task with id", description = "You can update Task with id. Access to this method : ADMIN and INSTRUCTOR")
    public SimpleResponse updateTask(@RequestParam Long taskId, @RequestBody @Valid TaskRequest taskRequest) {
        return taskService.updateTask(taskId, taskRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR,STUDENT')")
    @Operation(summary = "This method can delete Task with id", description = "You can delete Task with id. Access to this method : ADMIN and INSTRUCTOR")
    public SimpleResponse deleteTask(@RequestParam Long taskId) {
        return taskService.deleteTaskById(taskId);
    }
}