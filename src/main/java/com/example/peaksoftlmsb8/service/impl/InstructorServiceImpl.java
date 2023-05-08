package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Instructor;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.InstructorRequest;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.PaginationResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.InstructorRepository;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
        PaginationResponseForInstructor paginationResponse = new PaginationResponseForInstructor();
        paginationResponse.setInstructorResponses(pageInstructor.getContent());
        paginationResponse.setCurrentPage(pageInstructor.getNumber());
        paginationResponse.setPageSize(pageInstructor.getNumber());

        return paginationResponse;
    }

    @Override
    public InstructorResponse findByInstructorId(Long instructorId) {
        return instructorRepository.getByInstructorId(instructorId)
                .orElseThrow(() -> new NotFoundException("this id = " + instructorId + " not found !"));
    }

    @Override
    public SimpleResponse saveInstructor(InstructorRequest instructorRequest) {
        if (userRepository.existsByEmail(instructorRequest.getEmail())) {
            throw new AlReadyExistException("This email " + instructorRequest.getEmail() + " already exists !");
        }
        Instructor instructor = new Instructor();
        universalMethod(instructorRequest, instructor);
        return new SimpleResponse(HttpStatus.OK, "This " + instructor.getUser().getFirstName() + " saved...");
    }

    @Override
    public SimpleResponse updateInstructor(Long instructorId, InstructorRequest newInstructor) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new NotFoundException("this id = " + instructorId + " not found!"));
        universalMethod(newInstructor, instructor);
        return new SimpleResponse(HttpStatus.OK, "This " +
                instructor.getUser().getFirstName() + " updated on " +
                newInstructor.getFirstName());
    }

    @Override
    public SimpleResponse deleteInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new NotFoundException("this id = " + instructorId + " not found !"));
        for (Course course : instructor.getCourses()) {
            course.setInstructors(null);
        }
        instructorRepository.delete(instructor);
        return new SimpleResponse(HttpStatus.OK, "This " + instructor.getUser().getFirstName() + " deleted...");
    }

    private void universalMethod(InstructorRequest instructorRequest, Instructor instructor) {
        instructor.getUser().setFirstName(instructorRequest.getFirstName());
        instructor.getUser().setLastName(instructorRequest.getLastName());
        instructor.getUser().setPassword(passwordEncoder.encode(instructorRequest.getPassword()));
        instructor.getUser().setEmail(instructorRequest.getEmail());
        instructor.getUser().setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setSpecial(instructorRequest.getSpecial());
        instructorRepository.save(instructor);
    }
}
