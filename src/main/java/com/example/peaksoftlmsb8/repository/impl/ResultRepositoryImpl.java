package com.example.peaksoftlmsb8.repository.impl;

import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForInstructor;
import com.example.peaksoftlmsb8.repository.ResultRepository;
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
        System.out.println("hjdkfalg;");
        String sql = """
    SELECT
        r.id AS result_of_test_id,
        CONCAT(u.first_name, ' ', u.last_name) AS full_name,
        r.count_correct AS count_correct,
        r.count_in_correct AS count_in_correct
    FROM
        result_of_tests r
    JOIN
        students s ON s.id = r.student_id
    JOIN
        users u ON s.id = u.student_id
    WHERE
        r.test_id = ?
""";

        return jdbcTemplate.query(sql, (resulSet, i) -> {
            ResultOfTestResponseForInstructor responseForInstructor = new ResultOfTestResponseForInstructor();
            responseForInstructor.setResultOfTestId(resulSet.getLong("result_of_test_id"));
            responseForInstructor.setStudentFullName(resulSet.getString("full_name"));
            responseForInstructor.setCorrectAnswers(resulSet.getInt("count_correct"));
            responseForInstructor.setWrongAnswers(resulSet.getInt("count_in_correct"));
            return responseForInstructor;
        }, testId);
    }
}
//    concat(u.first_name,' ', u.last_name) as full_name,
//join users u on s.id = u.student_id