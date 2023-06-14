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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(TaskAnswerServiceImpl.class);


    @Override
    public SimpleResponse sendTaskAnswer(Long taskId, TaskAnswerRequest taskAnswerRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        logger.info("Token has been taken!");
        User user = userRepository.findByEmail(login).orElseThrow(() -> {
            logger.error("User not found!");
            throw new NotFoundException("Пользователь не найден!");
        });
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException("Задача с идентификатором: " + taskId + " не найдена!"));
        if (task.getTaskAnswers().stream().anyMatch(t -> t.getStudent().getUser().equals(user))) {
            logger.error("One user can only answer one task");
            throw new BadRequestException("Один пользователь может ответить только на одну задачу");
        }
        if (!task.getTaskAnswers().stream().anyMatch(t -> t.getStudent().getUser().getRole().equals(Role.STUDENT))) {
            logger.error("Sorry, only students can submit an answer!");
            throw new BadRequestException("Извините, только студенты могут отправить ответ!");
        }
        TaskAnswer taskAnswer = new TaskAnswer();
        if (taskAnswerRequest.getTaskValue().equals("") || taskAnswerRequest.getTaskValue() == null) {
            logger.error("Task answer value cannot be null");
            throw new BadRequestException("Значение ответа на задачу не может быть нулевым");
        }
        taskAnswer.setTaskValue(taskAnswerRequest.getTaskValue());
        taskAnswer.setTask(task);
        taskAnswer.setStudent(user.getStudent());
        task.setTaskAnswers(List.of(taskAnswer));
        taskAnswerRepository.save(taskAnswer);
        logger.info("Response to task with id: " + taskAnswer.getId() +  " successfully saved");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ответ на задачу с идентификатором: " + taskAnswer.getId() + " успешно сохранен!")
                .build();
    }

    @Override
    public SimpleResponse update(Long taskAnswerId, TaskAnswerRequest taskAnswerRequest) {
        TaskAnswer taskAnswer = taskAnswerRepository.findById(taskAnswerId).orElseThrow(
                () -> {
                    logger.error("Response to task with id: " + taskAnswerId +  " not found!");
                    throw  new NotFoundException("Ответ на задачу с идентификатором: " + taskAnswerId + " не найден!");});
        if (!taskAnswerRequest.getTaskValue().equals(taskAnswer.getTaskValue())) {
            taskAnswer.setTaskValue(taskAnswerRequest.getTaskValue());
        }
        taskAnswerRepository.save(taskAnswer);
        logger.info("Response to task with id: " + taskAnswerId +  " successfully updated");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ответ на задачу с идентификатором: " + taskAnswerId + " успешно обновлен!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long taskAnswerId) {
        if (!taskAnswerRepository.existsById(taskAnswerId)) {
            logger.error("Response to task with id: " + taskAnswerId +  " not found!");
            throw new BadRequestException("Ответ на задачу с идентификатором: " + taskAnswerId + " не найден!");
        }
        taskAnswerRepository.deleteById(taskAnswerId);
        logger.info("Response to task with id: " + taskAnswerId +  " successfully deleted");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ответ на задачу с идентификатором: " + taskAnswerId + " успешно удален!")
                .build();
    }

    @Override
    public List<TaskAnswerResponse> findTaskAnswerByTaskId(Long taskId, String taskAnswerStatus) {
        if (!taskRepository.existsById(taskId)) {
            logger.error("Task   with id: "+ taskId+ " not found");
            throw new BadRequestException("Задача с идентификатором: " + taskId + " не найдена!");
        }
        List<TaskAnswerResponse> taskAnswerResponses = taskAnswerRepository.findAllByTaskId(taskId);
        if (taskAnswerResponses.isEmpty()) {
            logger.error("Task responses  with id: "+ taskId+ " not found");
            throw new NotFoundException("Ответы задачи с идентификатором задачи: " + taskId + " не найдены!");
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