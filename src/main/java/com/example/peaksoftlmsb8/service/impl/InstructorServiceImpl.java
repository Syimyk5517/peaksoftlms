package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Instructor;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.instructor.InstructorRequest;
import com.example.peaksoftlmsb8.dto.response.instructor.InstructorResponse;
import com.example.peaksoftlmsb8.dto.response.instructor.PaginationResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.InstructorRepository;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.EmailSenderService;
import com.example.peaksoftlmsb8.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final EmailSenderService emailSenderService;
    private static final Logger logger = LogManager.getLogger(InstructorServiceImpl.class);


    @Override
    public PaginationResponseForInstructor getAllInstructors(int size, int page, String search, String sortBy) {
        if (!"name_asc".equals(sortBy) &&
                !"name_desc".equals(sortBy) &&
                sortBy != null) {
            logger.error("you wrote the wrong name , write like this name_asc or name_desc");
            throw new NotFoundException("вы написали неправильное имя, напишите так name_asc или name_desc");
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstructorResponse> pageInstructor = instructorRepository.getAll(pageable, search, sortBy);
        PaginationResponseForInstructor paginationResponse = new PaginationResponseForInstructor();
        paginationResponse.setInstructorResponses(pageInstructor.getContent());
        paginationResponse.setCurrentPage(pageInstructor.getNumber() + 1);
        paginationResponse.setPageSize(pageInstructor.getTotalPages());

        return paginationResponse;
    }

    @Override
    public InstructorResponse findByInstructorId(Long instructorId) {
        return instructorRepository.getByInstructorId(instructorId)
                .orElseThrow(() ->  {
                    logger.error("this id = " + instructorId + " not found !");
                    throw new NotFoundException("Этот идентификатор = " + instructorId + " не найден!");
                });
    }

    @Override
    public SimpleResponse saveInstructor(InstructorRequest instructorRequest) {
        if (userRepository.existsByEmail(instructorRequest.getEmail())) {
            logger.error("This email " + instructorRequest.getEmail() + " already exists !");
            throw new AlReadyExistException("Это электронное письмо: " + instructorRequest.getEmail() + " уже существует!");
        }
        Instructor instructor = new Instructor();
        User user = new User();
        user.setFirstName(instructorRequest.getFirstName());
        user.setLastName(instructorRequest.getLastName());
        user.setEmail(instructorRequest.getEmail());
        user.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setSpecial(instructorRequest.getSpecial());
        instructor.setUser(user);
        user.setInstructor(instructor);
        emailSenderService.emailSender(instructorRequest.getEmail(), instructorRequest.getLink());
        instructorRepository.save(instructor);

        logger.info("This " + instructorRequest.getFirstName() + " saved...");
        return new SimpleResponse(HttpStatus.OK, "Это " + instructorRequest.getFirstName() + " сохранено...");
    }

    @Override
    public SimpleResponse updateInstructor(Long instructorId, InstructorRequest newInstructor) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("this id = " + instructorId + " not found!");
                    throw new NotFoundException("Этот идентификатор = " + instructorId + " не найден");});
        instructor.getUser().setFirstName(newInstructor.getFirstName());
        instructor.getUser().setLastName(newInstructor.getLastName());
        instructor.getUser().setEmail(newInstructor.getEmail());
        instructor.getUser().setPhoneNumber(newInstructor.getPhoneNumber());
        instructor.setSpecial(newInstructor.getSpecial());
        instructorRepository.save(instructor);
        logger.info("This " + instructor.getUser().getFirstName() + " updated on " + newInstructor.getFirstName());
        return new SimpleResponse(HttpStatus.OK, "Это " +
                instructor.getUser().getFirstName() + " обновлено " +
                newInstructor.getFirstName());
    }

    @Override
    public SimpleResponse deleteInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() ->{
                    logger.error("this id = " + instructorId + " not found !");
                    throw new NotFoundException("Этот идентификатор = " + instructorId + " не найден!");
                });
        for (Course course : instructor.getCourses()) {
            course.setInstructors(null);
        }
        instructorRepository.delete(instructor);
        logger.info("This " + instructor.getUser().getFirstName() + " deleted...");
        return new SimpleResponse(HttpStatus.OK, "Успешно удалено");
    }

}
