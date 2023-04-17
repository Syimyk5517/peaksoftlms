package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.dto.response.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
//    @Query("select ")
//    StudentResponse findById(Long studentId);
}
