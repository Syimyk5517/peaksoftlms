package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Task;
import com.example.peaksoftlmsb8.db.entity.TaskAnswer;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.exception.BadRequestException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.TaskAnswerRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TaskAnswerResponse;
import com.example.peaksoftlmsb8.repository.TaskAnswerRepository;
import com.example.peaksoftlmsb8.repository.TaskRepository;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.TaskAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskAnswerServiceImpl implements TaskAnswerService {
    private final TaskAnswerRepository taskAnswerRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse sendTaskAnswer(Long taskId, TaskAnswerRequest taskAnswerRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("Token has been taken!");
        User user = userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("User not found!");
            throw new NotFoundException("User not found!");
        });
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException("Task with ID: " + taskId + " is not found !"));
        if (task.getTaskAnswers().stream().anyMatch(t -> t.getStudent().getUser().equals(user))) {
            throw new BadRequestException("One user can only answer one task");
        }
        if (!task.getTaskAnswers().stream().anyMatch(t -> t.getStudent().getUser().getRole().equals(Role.STUDENT))) {
            throw new BadRequestException("Sorry, only students can submit an answer!");
        }
//        if (!task.getTaskAnswers().isEmpty()) {
//            throw new BadRequestException("This task has an answer, you cannot add a new answer");
        TaskAnswer taskAnswer = new TaskAnswer();
        if (taskAnswerRequest.getTaskValue().equals("") || taskAnswerRequest.getTaskValue() == null) {
            throw new BadRequestException("Task answer value cannot be null");
        }
        taskAnswer.setTaskValue(taskAnswerRequest.getTaskValue());
        taskAnswer.setTask(task);
        taskAnswer.setStudent(user.getStudent());
        task.setTaskAnswers(List.of(taskAnswer));
        taskAnswerRepository.save(taskAnswer);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task answer with ID: " + taskAnswer.getId() + " is successfully saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long taskAnswerId, TaskAnswerRequest taskAnswerRequest) {
        TaskAnswer taskAnswer = taskAnswerRepository.findById(taskAnswerId).orElseThrow(
                () -> new NotFoundException("Task answer with ID: " + taskAnswerId + " is not found !"));
        if (!taskAnswerRequest.getTaskValue().equals(taskAnswer.getTaskValue())) {
            taskAnswer.setTaskValue(taskAnswerRequest.getTaskValue());
        }
        taskAnswerRepository.save(taskAnswer);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task answer with ID: " + taskAnswerId + " is successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long taskAnswerId) {
        if (!taskAnswerRepository.existsById(taskAnswerId)) {
            throw new BadRequestException("Task answer with ID: " + taskAnswerId + " is not found!");
        }
        taskAnswerRepository.deleteById(taskAnswerId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task answer with ID: " + taskAnswerId + " is successfully deleted!")
                .build();
    }

    @Override
    public List<TaskAnswerResponse> findTaskAnswerByTaskId(Long taskId, String taskAnswerStatus) {
        if (!taskRepository.existsById(taskId)) {
            throw new BadRequestException("Task with ID: " + taskId + " is not found!");
        }
        List<TaskAnswerResponse> taskAnswerResponses = taskAnswerRepository.findAllByTaskId(taskId);
        if (taskAnswerResponses.isEmpty()) {
            throw new NotFoundException("Task answers with task ID: " + taskId + " is not found!");
        }
        if (taskAnswerStatus != null) {
            if (taskAnswerResponses.stream().filter(ta -> ta.getTaskStatus() != null).count() > 0) {
                return taskAnswerResponses.stream().filter(t -> t.getTaskStatus().name().equals(taskAnswerStatus)
                        && t.getTaskStatus() != null).toList();
            }
            return null;
        } else {
            return taskAnswerResponses;
        }
    }
}