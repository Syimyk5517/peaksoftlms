package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}