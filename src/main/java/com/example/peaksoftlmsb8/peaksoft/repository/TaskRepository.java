package com.example.peaksoftlmsb8.peaksoft.repository;

import com.example.peaksoftlmsb8.peaksoft.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}