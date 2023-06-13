package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Task;
import com.example.peaksoftlmsb8.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.task.TaskRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.task.TaskResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.TaskRepository;
import com.example.peaksoftlmsb8.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);


    @Override
    public TaskResponse getByTaskId(Long taskId) {
        return taskRepository.findByIdTask(taskId)
                .orElseThrow(() -> {
                    logger.error("Task with id: "+taskId+" not found!");
                    throw new NotFoundException("Задача с id:" + taskId + "не найдена");});
    }

    @Override
    public List<TaskResponse> getAllTaskByLessonId(Long lessonId) {
        logger.info("Successfully get all task by lesson!");
        return taskRepository.findAllTaskByLessonId(lessonId);
    }

    @Override
    public SimpleResponse saveTask(TaskRequest taskRequest) {
        Lesson lesson = lessonRepository.findById(taskRequest.getLessonId())
                .orElseThrow(() ->{
                    logger.error("Lesson with id: "+ taskRequest.getLessonId()+" not found!");
                throw new NotFoundException("Урок с идентификатором: " + taskRequest.getLessonId() + " не найден");});
        if (taskRepository.existsByName(taskRequest.getName())) {
            logger.error("Task with name "+taskRequest.getName()+ " all ready exist!");
            throw new AlReadyExistException("Задача с именем: " + taskRequest.getName() + " уже существует");
        }
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setFile(taskRequest.getFile());
        task.setDeadline(taskRequest.getDeadline());
        task.setLesson(lesson);
        taskRepository.save(task);

        logger.info("Successfully saved!");
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно сохранено")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse updateTask(Long taskId, TaskRequest newTaskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->{
                    logger.error("Task with id: "+ taskId+ " not found!");
                 throw new NotFoundException("Задача с id: " + taskId + " не найдена");});
        if (taskRepository.existsByName(newTaskRequest.getName())) {
            logger.error("Task with name : + newTaskRequest.getName() + already exists");
            throw new AlReadyExistException("Task with name :" + newTaskRequest.getName() + " already exists");
        }
        task.setName(newTaskRequest.getName());
        task.setDescription(newTaskRequest.getDescription());
        task.setFile(newTaskRequest.getFile());
        task.setDeadline(newTaskRequest.getDeadline());
        taskRepository.save(task);
        logger.info("Successfully update!");
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно обновлено")
                .build();
    }

    @Override
    public SimpleResponse deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
        { logger.error("Presentation with id: "+taskId+ " not found!");
               throw  new NotFoundException("Презентация с идентификатором: " + taskId + " не найдена");});
        taskRepository.delete(task);
        logger.info("Successfully deleted!");
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно удалено")
                .build();
    }
}
