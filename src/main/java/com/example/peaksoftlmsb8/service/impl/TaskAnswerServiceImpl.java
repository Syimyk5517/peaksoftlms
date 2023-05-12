package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.TaskAnswer;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.TaskPointRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.TaskAnswerRepository;
import com.example.peaksoftlmsb8.service.TaskAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskAnswerServiceImpl implements TaskAnswerService {
    private final TaskAnswerRepository taskAnswerRepository;

    @Override
    public SimpleResponse saveTaskPoint(TaskPointRequest taskPointRequest) {
        TaskAnswer taskAnswer = taskAnswerRepository.findById(taskPointRequest.getTaskAnswerId()).orElseThrow(() ->
                new NotFoundException(String.format("Task answer with id: " + taskPointRequest.getTaskAnswerId() + " not found")));
        taskAnswer.setPoint(taskPointRequest.getPoint());
        taskAnswerRepository.save(taskAnswer);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved").build();
    }

    @Override
    public SimpleResponse updateTaskPoint(TaskPointRequest taskPointRequest) {
        TaskAnswer taskAnswer = taskAnswerRepository.findById(taskPointRequest.getTaskAnswerId()).orElseThrow(() ->
                new NotFoundException(String.format("Task answer with id: " + taskPointRequest.getTaskAnswerId() + " not found")));
        taskAnswer.setPoint(taskPointRequest.getPoint());
        taskAnswerRepository.save(taskAnswer);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse deleteTaskPoint(Long taskAnswerId) {
        TaskAnswer taskAnswer = taskAnswerRepository.findById(taskAnswerId).orElseThrow(() ->
                new NotFoundException(String.format("Task answer with id: " + taskAnswerId + " not found")));
        taskAnswer.setPoint(null);
        taskAnswerRepository.save(taskAnswer);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

}
