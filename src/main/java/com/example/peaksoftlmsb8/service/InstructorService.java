package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponse;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
public interface InstructorService {
    PaginationResponse getAllInstructors(int size, int page, String sort, String keyWOrd);

    InstructorResponse findByIdInstructor(Long instructorId);
}
