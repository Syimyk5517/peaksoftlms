package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.task.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.task.TaskResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.TaskRepository;
import com.example.peaksoftlmsb8.service.TaskService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;
    private final JwtService jwtService;
    private final JavaMailSender javaMailSender;

    @Override
    public TaskResponse getByTaskId(Long taskId) {
        return taskRepository.findByIdTask(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id:" + taskId + " not found")));
    }

    @Override
    public List<TaskResponse> getAllTaskByLessonId(Long lessonId) {
        return taskRepository.findAllTaskByLessonId(lessonId);
    }

    @Override
    public SimpleResponse saveTask(TaskRequest taskRequest) {
        Lesson lesson = lessonRepository.findById(taskRequest.getLessonId())
                .orElseThrow(() -> new NotFoundException((String.format("Lesson with id: " + taskRequest.getLessonId() + " not found"))));
        if (taskRepository.existsByName(taskRequest.getName())) {
            throw new AlReadyExistException("Task with name :" + taskRequest.getName() + " already exists");
        }
        User accountInToken = jwtService.getAccountInToken();
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setFile(taskRequest.getFile());
        task.setDeadline(taskRequest.getDeadline());
        task.setLesson(lesson);
        taskRepository.save(task);
        Course course = lesson.getCourse();
        for (Group group: course.getGroups()) {
            for (Student student:group.getStudents()) {
                sendEmail(student.getUser().getEmail(),
                        String.format("%s %s добавиль новый задачу ",accountInToken.getFirstName(),accountInToken.getLastName()),
                        String.format("название задачи : %s",task.getName()),
                        task.getDeadline());
            }
        }

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved!")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse updateTask(Long taskId, TaskRequest newTaskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id:" + taskId + "not found"));
        if (taskRepository.existsByName(newTaskRequest.getName())) {
            throw new AlReadyExistException("Task with name :" + newTaskRequest.getName() + " already exists");
        }
        task.setName(newTaskRequest.getName());
        task.setDescription(newTaskRequest.getDescription());
        task.setFile(newTaskRequest.getFile());
        task.setDeadline(newTaskRequest.getDeadline());
        taskRepository.save(task);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new NotFoundException(String.format("Presentation with id: " + taskId + " not found")));
        taskRepository.delete(task);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted!")
                .build();
    }
    private void sendEmail(String toEmail, String text, String taskName,LocalDate deadline ){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("syimykjumabekuulu@gmail.com");
            helper.setText(text);
            helper.setText(taskName);
            helper.setSentDate(Date.valueOf(deadline));
            helper.setTo(toEmail);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new NotFoundException("Email send failed!");
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found and returned null!");
        }
    }

}
