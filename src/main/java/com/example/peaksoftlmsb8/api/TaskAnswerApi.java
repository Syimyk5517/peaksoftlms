package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.TaskAnswerRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TaskAnswerResponse;
import com.example.peaksoftlmsb8.service.TaskAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/task_answers")
@RequiredArgsConstructor
@Tag(name = "Task_Answer")
public class TaskAnswerApi {
    private final TaskAnswerService taskAnswerService;

    @PostMapping
    @Operation(summary = "Submit an answer to a task",
            description = "The method allows students to submit an answer to an assignment." +
                    "You can choose in the Task the answer format only TEXT, LINK, IMAGE, CODE, FILE")
    public SimpleResponse sendTaskAnswer(@RequestParam Long taskId,
                                         @RequestBody TaskAnswerRequest taskAnswerRequest) {
        return taskAnswerService.sendTaskAnswer(taskId, taskAnswerRequest);
    }

    @PutMapping
    @Operation(summary = "Update the answer to the question",
            description = "The method allows students to update their answer to the assignment." +
                    "You can choose in the Task the answer format only TEXT, LINK, IMAGE, CODE, FILE")
    public SimpleResponse update(@RequestParam Long taskAnswerId,
                                 @RequestBody TaskAnswerRequest taskAnswerRequest) {
        return taskAnswerService.update(taskAnswerId, taskAnswerRequest);
    }

    @DeleteMapping
    @Operation(summary = "Delete an answer to a task",
            description = "The method allows teachers and administrators to delete the answer to the assignment")
    public SimpleResponse delete(@RequestParam Long taskAnswerId) {
        return taskAnswerService.delete(taskAnswerId);
    }

    @GetMapping
    @Operation(summary = "Find answers to a task by task ID and sort by answer status",
            description = "The method allows teachers and administrators to find answers to a task by task ID." +
                    "Answers to tasks have 5 statuses: EXPECTATION, ACCEPTED, NOT_ACCEPTED, DELAY, NOT_PASSED.")
    public List<TaskAnswerResponse> findTaskAnswersByTaskId(@RequestParam Long taskId,
                                                            @RequestParam(required = false) String taskAnswerStatus) {
        return taskAnswerService.findTaskAnswerByTaskId(taskId,taskAnswerStatus);
    }
}
