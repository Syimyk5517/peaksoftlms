package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.LinkRequest;
import com.example.peaksoftlmsb8.dto.response.LinkResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.LinkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/link")
@RequiredArgsConstructor
@Tag(name = "Links")
@PostAuthorize("hasAnyAuthority ('INSTRUCTOR','STUDENT')")
public class LinkApi {

    private final LinkService linkService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public LinkResponse getLink(@RequestParam Long lessonId) {
        return linkService.getLinkFromLesson(lessonId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public SimpleResponse saveLinkLesson(@RequestBody LinkRequest linkRequest) {
        return linkService.addLinkToLesson(linkRequest);
    }


    @PutMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public SimpleResponse updateLinkInLesson(@RequestBody LinkRequest linkRequest) {
        return linkService.updateLinkInLesson(linkRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public SimpleResponse removeLinkLesson(@RequestParam Long lessonId) {
        return linkService.removeLinkFromLesson(lessonId);
    }
}
