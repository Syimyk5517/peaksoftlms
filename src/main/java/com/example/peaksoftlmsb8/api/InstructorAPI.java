package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponse;
import com.example.peaksoftlmsb8.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorAPI {
    private final InstructorService instructorService;

    @GetMapping("/{sort}")
    public PaginationResponse getAll(@RequestParam int size,
                                     @RequestParam int page,
                                     @PathVariable String sort,
                                     @RequestBody String keyWOrd) {
       return instructorService.getAllInstructors(size,page,sort,keyWOrd);
    }
    @GetMapping("/{instructorId}")
    public InstructorResponse findByIdInstructor(@PathVariable Long instructorId){
        return instructorService.findByIdInstructor(instructorId);
    }
}
