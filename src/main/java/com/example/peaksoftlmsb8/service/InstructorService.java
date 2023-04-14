package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.InstructorRequest;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
public interface InstructorService {
    PaginationResponseForInstructor getAllInstructors(int size, int page, String sort, String keyWOrd);

    InstructorResponse findByInstructorId(Long instructorId);

    SimpleResponse saveInstructor(InstructorRequest instructorRequest);

    SimpleResponse updateInstructor(Long instructorId, InstructorRequest newInstructor);

    SimpleResponse deleteInstructorById(Long instructorId);
}
