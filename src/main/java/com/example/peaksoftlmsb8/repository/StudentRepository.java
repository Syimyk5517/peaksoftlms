package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}