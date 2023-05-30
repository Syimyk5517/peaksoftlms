package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForInstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor

public class ResultRepositoryImpl implements ResultRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<ResultOfTestResponseForInstructor> resultTest(Long testId) {
         String sql = """
                 select s.fist_name as first_name,
                 s.last_name as last_name,
                 r.count_correct as count_correct,
                 r.count_in_correct as count_in_correct
                
             
                 """;
        return null;
    }
}
