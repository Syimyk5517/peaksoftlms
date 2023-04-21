package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.InstructorRequest;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorAPI {
    private final InstructorService instructorService;

    @GetMapping
    public PaginationResponseForInstructor getAll(@RequestParam int size,
                                                  @RequestParam int page,
                                                  @RequestParam String sort,
                                                  @RequestBody String keyWOrd) {
        return instructorService.getAllInstructors(size, page, sort, keyWOrd);
    }

    @GetMapping("/{instructorId}")
    public InstructorResponse findByInstructorId(@PathVariable Long instructorId) {
        return instructorService.findByInstructorId(instructorId);
    }

    @PostMapping
    public SimpleResponse saveInstructor(@RequestBody InstructorRequest instructorRequest) {
        return instructorService.saveInstructor(instructorRequest);
    }

    @PutMapping("/{instructorId}")
    public SimpleResponse updateInstructor(@PathVariable Long instructorId,
                                           @RequestBody InstructorRequest newInstructor) {
        return instructorService.updateInstructor(instructorId, newInstructor);
    }

    @DeleteMapping("/{instructorId}")
    public SimpleResponse deleteInstructorById(@PathVariable Long instructorId) {
        return instructorService.deleteInstructorById(instructorId);
    }
}
