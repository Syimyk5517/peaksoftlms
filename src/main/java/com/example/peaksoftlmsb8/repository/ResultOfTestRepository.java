package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.ResultOfTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResultOfTestRepository extends JpaRepository<ResultOfTest, Long> {
    @Query("select r from ResultOfTest r where  r.test.id =?1")
    ResultOfTest findResultOfTestById(Long testId);
}