package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.VideoLessonRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.VideoLessonResponse;
import com.example.peaksoftlmsb8.service.VideoLessonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoLessonApi {
    private final VideoLessonService videoLessonService;

    @Operation(summary = "This method saves video", description = "You can save Video in Database")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public SimpleResponse save(@RequestBody @Valid VideoLessonRequest videoLessonRequest,
                               @RequestParam Long lessonId) {
        return videoLessonService.save(videoLessonRequest, lessonId);
    }

    @Operation(summary = "This method gets video with ID", description = "You can get Video with ID")
    @GetMapping("/getById")
    public VideoLessonResponse findById(@RequestParam Long videoId) {
        return videoLessonService.getVideoLessonById(videoId);
    }

    @Operation(summary = "This method gets all Videos", description = "You can get all Videos")
    @GetMapping("/findAll")
    public List<VideoLessonResponse> findAllVideos() {
        return videoLessonService.findAllVideos();
    }

    @Operation(summary = "This method gets all Videos with Lesson's ID", description = "You can get all Videos")
    @GetMapping("/findByLessonId")
    @PreAuthorize("permitAll()")
    public List<VideoLessonResponse> findByLessonId(@RequestParam Long lessonId) {
        return videoLessonService.findByLessonId(lessonId);
    }

    @Operation(summary = "This method updates the Video with ID", description = "You can update Video with ID")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public SimpleResponse update(@RequestParam Long videoId,
                                 @RequestBody VideoLessonRequest videoLessonRequest) {
        return videoLessonService.update(videoId, videoLessonRequest);
    }

    @Operation(summary = "This method removes Video with ID", description = "You can delete Video with ID")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public SimpleResponse delete(@RequestParam Long videoId) {
        return videoLessonService.delete(videoId);
    }
}
