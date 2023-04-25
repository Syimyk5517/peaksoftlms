package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.InstructorRequest;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorAPI {
    private final InstructorService instructorService;

    @GetMapping
    public PaginationResponseForInstructor getAll(@RequestParam int size,
                                                  @RequestParam int page,
                                                  @RequestParam String sort,
                                                  @RequestBody String keyWord) {
        return instructorService.getAllInstructors(size, page, sort, keyWord);
    }

    @GetMapping("/findById")
    public InstructorResponse findByInstructorId(@RequestParam Long instructorId) {
        return instructorService.findByInstructorId(instructorId);
    }

    @PostMapping
    public SimpleResponse saveInstructor(@RequestBody InstructorRequest instructorRequest) {
        return instructorService.saveInstructor(instructorRequest);
    }

    @PutMapping
    public SimpleResponse updateInstructor(@RequestParam Long instructorId,
                                           @RequestBody InstructorRequest newInstructor) {
        return instructorService.updateInstructor(instructorId, newInstructor);
    }

    @DeleteMapping
    public SimpleResponse deleteInstructorById(@RequestParam Long instructorId) {
        return instructorService.deleteInstructorById(instructorId);
    }
}
