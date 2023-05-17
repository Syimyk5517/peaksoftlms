package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.ResultOfTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultOfTestRepository extends JpaRepository<ResultOfTest, Long> {

    @Modifying
    @Query("delete from ResultOfTest where test.id = ?1")
    void deleteByTest_Id(Long testId);

    @Query("select r from ResultOfTest r where  r.test.id =?1")
    Optional<ResultOfTest> findResultOfTestById(Long testId);

    void deleteByStudentId(Long studentId);
}