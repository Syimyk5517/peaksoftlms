package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.ContentRequest;
import com.example.peaksoftlmsb8.dto.response.ContentResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.ContentService;
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
public class ContentApi {
    private final ContentService contentService;

    @PostMapping
    @Operation(summary = "This method send task's answer and save this",
            description = "Only Students send answer")
    public SimpleResponse sendTaskAnswer(@RequestParam Long taskId,
                                         @RequestBody ContentRequest contentRequest) {
        return contentService.sendTaskAnswer(taskId, contentRequest);
    }

    @PostMapping("/update")
    @Operation(summary = "This method update task's answer",
            description = "Only Students update answer")
    public SimpleResponse update(@RequestParam Long contentId,
                                 @RequestBody ContentRequest contentRequest) {
        return contentService.update(contentId, contentRequest);
    }

    @DeleteMapping
    @Operation(summary = "This method delete task's answer",
            description = "Only Instructors and Admin delete answer")
    public SimpleResponse delete(@RequestParam Long contentId) {
        return contentService.delete(contentId);
    }

    @GetMapping
    @Operation(summary = "This method find task's answers with task ID",
            description = "Only Instructors and Admin can find")
    public List<ContentResponse> findContentsByTaskId(@RequestParam Long taskId) {
        return contentService.findContentsByTaskId(taskId);
    }
}
