package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.InstructorRequest;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

public interface InstructorService {
    PaginationResponseForInstructor getAllInstructors(int size, int page, String search, String sort);
    //PaginationResponseForInstructor getAllInstructorsAA(int size, int page);

    InstructorResponse findByInstructorId(Long instructorId);

    SimpleResponse saveInstructor(InstructorRequest instructorRequest);

    SimpleResponse updateInstructor(Long instructorId, InstructorRequest newInstructor);

    SimpleResponse deleteInstructorById(Long instructorId);
}
