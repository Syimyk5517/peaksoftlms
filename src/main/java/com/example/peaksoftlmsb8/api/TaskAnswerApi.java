package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.TaskPointRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.TaskAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/task_answers")
@RequiredArgsConstructor
@Tag(name = "Task_Answers")
public class TaskAnswerApi {
    private final TaskAnswerService taskAnswerService;

    @PostMapping("/checkTask")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Save a new check task", description = "Save a new check task with the provided details")
    public SimpleResponse saveTaskPoint(@RequestBody @Valid TaskPointRequest taskPointRequest) {
        return taskAnswerService.saveTaskPoint(taskPointRequest);
    }

    @PutMapping("/checkTask")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Update a task point", description = "Update a task point with the provided task answer id using the information provided in the request body")
    public SimpleResponse updateTaskPoint(@RequestBody @Valid TaskPointRequest taskPointRequest) {
        return taskAnswerService.updateTaskPoint(taskPointRequest);
    }

    @DeleteMapping("/checkTask")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Delete a task point by task answer id", description = "Deletes a task point with the specified ID. This action requires admin or instructor authorization")
    public SimpleResponse deleteTaskPoint(@RequestParam Long taskAnswerId) {
        return taskAnswerService.deleteTaskPoint(taskAnswerId);
    }
}

