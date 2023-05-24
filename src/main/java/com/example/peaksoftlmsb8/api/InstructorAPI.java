package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.instructor.InstructorRequest;
import com.example.peaksoftlmsb8.dto.response.instructor.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.instructor.PaginationResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
@Tag(name = "Instructors")
@PostAuthorize("hasAuthority('ADMIN')")
public class InstructorAPI {
    private final InstructorService instructorService;

    @Operation(summary = "This method can get all Instructors ", description = "You can get Instructors with sort or search")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public PaginationResponseForInstructor getAll(@RequestParam int size,
                                                  @RequestParam int page,
                                                  @RequestParam(required = false) String search,
                                                  @RequestParam(required = false) String sort) {
        return instructorService.getAllInstructors(size, page, search, sort);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This method can get Instructor with ID", description = "You can get Instructor with ID")
    @GetMapping("/findById")
    public InstructorResponse findByInstructorId(@RequestParam Long instructorId) {
        return instructorService.findByInstructorId(instructorId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This method can save Instructor", description = "You can save Instructor in Database")
    @PostMapping
    public SimpleResponse saveInstructor(@RequestBody InstructorRequest instructorRequest) {
        return instructorService.saveInstructor(instructorRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This method can update Instructor with ID", description = "You can update Instructor with ID")
    @PutMapping
    public SimpleResponse updateInstructor(@RequestParam Long instructorId,
                                           @RequestBody InstructorRequest newInstructor) {
        return instructorService.updateInstructor(instructorId, newInstructor);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This method can delete Instructor with ID", description = "You can delete Instructor with ID")
    @DeleteMapping
    public SimpleResponse deleteInstructorById(@RequestParam Long instructorId) {
        return instructorService.deleteInstructorById(instructorId);
    }
}
