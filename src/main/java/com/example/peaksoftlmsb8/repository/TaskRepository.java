package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Task;
import com.example.peaksoftlmsb8.dto.response.TaskResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Boolean existsByName(String name);

    @Query("select new com.example.peaksoftlmsb8.dto.response.TaskResponse" +
            "(t.id,t.name,t.description,t.file,t.deadline,t.lesson.id) " +
            "from Task t join Lesson  l on t.lesson.id = l.id where t.id = :taskId")
    Optional<TaskResponse> findByIdTask(Long taskId);

    @Query("select new com.example.peaksoftlmsb8.dto.response.TaskResponse(t.id,t.name,t.description,t.file,t.deadline,t.lesson.id) " +
            "from Task t join Lesson where t.lesson.id = :lessonId")
    List<TaskResponse> findAllTaskByLessonId(Long lessonId);
}