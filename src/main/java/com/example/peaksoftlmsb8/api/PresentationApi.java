package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.PresentationRequest;
import com.example.peaksoftlmsb8.dto.response.PresentationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.PresentationService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Save a new presentation", description = "Save a new presentation with the provided details")
    public SimpleResponse savePresentation(@RequestBody @Valid PresentationRequest presentationRequest) {
        return presentationService.savePresentation(presentationRequest);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    @Operation(summary = "Get all presentations by lesson ID", description = "Returns a list of all presentations for a given lesson ID")
    public List<PresentationResponse> getAllPresentationsByLessonId(@RequestParam Long lessonId) {
        return presentationService.findAllPresentationsByLessonId(lessonId);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    @Operation(summary = "Get presentation by ID", description = "Get the presentation details for the provided ID")
    public PresentationResponse getPresentationById(@RequestParam Long presentationId) {
        return presentationService.findByPresentationId(presentationId);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Update a presentation", description = "Update a presentation with the provided presentationId using the information provided in the request body")

    public SimpleResponse updatePresentation(@RequestParam Long presentationId, @RequestBody @Valid PresentationRequest presentationRequest) {
        return presentationService.updatePresentation(presentationId, presentationRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Delete a presentation by ID", description = "Deletes a presentation with the specified ID. This action requires admin or instructor authorization")
    public SimpleResponse deletePresentation(@RequestParam Long presentationId) {
        return presentationService.deletePresentation(presentationId);
    }
}
