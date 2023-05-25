package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.TaskAnswer;
import com.example.peaksoftlmsb8.dto.response.TaskAnswerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAnswerRepository extends JpaRepository<TaskAnswer, Long> {
    @Query("select new com.example.peaksoftlmsb8.dto.response.TaskAnswerResponse" +
            "(ta.id,ta.taskValue,ta.taskStatus) from TaskAnswer ta where ta.task.id=:taskId")
    List<TaskAnswerResponse> findAllByTaskId(Long taskId);
}