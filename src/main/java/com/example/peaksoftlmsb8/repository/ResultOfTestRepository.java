package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.ResultOfTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultOfTestRepository extends JpaRepository<ResultOfTest, Long> {

    @Modifying
    @Query("delete from ResultOfTest where test.id = ?1")
    void deleteByTest_Id(Long testId);

    List<ResultOfTest> findResultOfTestByTestId(Long testId);

}