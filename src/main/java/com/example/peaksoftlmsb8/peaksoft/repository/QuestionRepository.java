package com.example.peaksoftlmsb8.peaksoft.repository;

import com.example.peaksoftlmsb8.peaksoft.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}