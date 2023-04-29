package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.PresentationRequest;
import com.example.peaksoftlmsb8.dto.response.PresentationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.PresentationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/presentations")
@RequiredArgsConstructor
@Tag(name = "Presentations")
public class PresentationApi {
    private final PresentationService presentationService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse savePresentation(@RequestBody @Valid PresentationRequest presentationRequest) {
        return presentationService.savePresentation(presentationRequest);
    }

    @GetMapping
    public List<PresentationResponse> getPresentationsByLessonId(@RequestParam Long lessonId) {
        return presentationService.findAllPresentationsByLessonId(lessonId);
    }

    @GetMapping("/getById")
    public PresentationResponse getPresentationById(@RequestParam Long presentationId) {
        return presentationService.findByPresentationId(presentationId);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse updatePresentation(@RequestParam Long presentationId, @RequestBody @Valid PresentationRequest presentationRequest) {
        return presentationService.updatePresentation(presentationId, presentationRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse deletePresentation(@RequestParam Long presentationId) {
        return presentationService.deletePresentation(presentationId);
    }
}
