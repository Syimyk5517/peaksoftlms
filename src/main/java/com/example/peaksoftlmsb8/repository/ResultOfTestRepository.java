package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.ResultOfTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultOfTestRepository extends JpaRepository<ResultOfTest, Long> {

    Optional<ResultOfTest> findResultOfTestById(Long testId);

    void deleteByStudentId(Long studentId);
}