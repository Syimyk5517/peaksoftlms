package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.ResultOfTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultOfTestRepository extends JpaRepository<ResultOfTest, Long> {
    ResultOfTest findResultOfTestById(Long testId);
}