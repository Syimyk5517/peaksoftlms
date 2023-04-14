package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponse;
import com.example.peaksoftlmsb8.repository.InstructorRepository;
import com.example.peaksoftlmsb8.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    @Override
    public PaginationResponse getAllInstructors(int size, int page, String sort, String keyWOrd) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<InstructorResponse> pageInstructor = instructorRepository.getAll(pageable, keyWOrd);
        List<InstructorResponse> instructorResponses = new ArrayList<>(pageInstructor.getContent().stream()
                .map(i -> new InstructorResponse(
                        i.getId(),
                        i.getLastName(),
                        i.getFirstName(),
                        i.getSpecial(),
                        i.getPhoneNumber(),
                        i.getEmail(),
                        i.getPassword()
                )).toList());
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setInstructorResponses(instructorResponses);
        paginationResponse.setPageSize(pageInstructor.getNumber());
        paginationResponse.setCurrentPage(pageInstructor.getSize());
        return paginationResponse;
    }
    @Override
    public InstructorResponse findByIdInstructor(Long instructorId) {
        return null;
    }
}
