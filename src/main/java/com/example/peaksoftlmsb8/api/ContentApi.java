package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.ContentRequest;
import com.example.peaksoftlmsb8.dto.response.ContentResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.ContentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contents")
@RequiredArgsConstructor
@Tag(name = "Task_Contents")
public class ContentApi {
    private final ContentService contentService;

    @PostMapping
    public SimpleResponse create(@RequestParam Long taskId,
                                 @RequestBody ContentRequest contentRequest) {
        return contentService.create(taskId, contentRequest);
    }

    @PostMapping("/update")
    public SimpleResponse update(@RequestParam Long contentId,
                                 @RequestBody ContentRequest contentRequest) {
        return contentService.update(contentId, contentRequest);
    }

    @DeleteMapping
    public SimpleResponse delete(@RequestParam Long contentId) {
        return contentService.delete(contentId);
    }

    @GetMapping
    public List<ContentResponse> findContentsByTaskId(@RequestParam Long taskId) {
        return contentService.findContentsByTaskId(taskId);
    }
}
