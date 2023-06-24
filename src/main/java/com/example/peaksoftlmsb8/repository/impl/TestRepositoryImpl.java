package com.example.peaksoftlmsb8.repository.impl;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.dto.response.test.*;
import com.example.peaksoftlmsb8.repository.TestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestRepositoryImpl implements TestRepo {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TestResponseForInstructor> findAll() {
        String sql = """
                SELECT l.id AS lesson_id,
                       l.name AS lesson_name,
                       t.id AS test_id,
                       t.name AS test_name,
                       t.date_test AS date_test
                FROM lessons l
                JOIN tests t ON l.id = t.lesson_id
                """;

        return jdbcTemplate.query(sql, (resultSet, i) -> {
            TestResponseForInstructor testResponse = new TestResponseForInstructor();
            testResponse.setLessonId(resultSet.getLong("lesson_id"));
            testResponse.setLessonName(resultSet.getString("lesson_name"));
            testResponse.setTestId(resultSet.getLong("test_id"));
            testResponse.setTestName(resultSet.getString("test_name"));
            testResponse.setDateTest(resultSet.getDate("date_test").toLocalDate());

            String questionSql = """
                    SELECT q.id AS question_id,
                           q.question_name AS question_name,
                           q.option_type AS option_type
                    FROM questions q
                    WHERE q.test_id = ?
                    """;

            List<QuestionResponse> questionResponses = jdbcTemplate.query(questionSql, (innerResultSet, j) -> {
                QuestionResponse questionResponse = new QuestionResponse();
                questionResponse.setQuestionId(innerResultSet.getLong("question_id"));
                questionResponse.setQuestionName(innerResultSet.getString("question_name"));
                questionResponse.setOptionType(OptionType.valueOf(innerResultSet.getString("option_type")));

                String optionSql = """
                        SELECT o.id AS id,
                               o.text AS text,
                               o.is_true AS is_true
                        FROM options o
                        WHERE o.question_id = ?
                        """;

                List<OptionResponse> optionResponses = jdbcTemplate.query(optionSql, (innermostResultSet, k) ->
                        new OptionResponse(innermostResultSet.getLong("id"),
                                innermostResultSet.getString("text"),
                                innermostResultSet.getBoolean("is_true")), questionResponse.getQuestionId());

                questionResponse.setOptionResponses(optionResponses);
                return questionResponse;
            }, testResponse.getTestId());

            testResponse.setQuestionResponses(questionResponses);
            return testResponse;
        });

    }

    @Override
    public TestResponse findById(Long testId) {
        String testSql = "SELECT t.lesson_id, t.id AS test_id, t.name AS test_name " +
                "FROM tests t WHERE t.id = ?";
        TestResponseForStudent testResponseForStudent1 = jdbcTemplate.queryForObject(testSql,
                (resultSet, i) -> {
                    TestResponseForStudent testResponseForStudent = new TestResponseForStudent();
                    testResponseForStudent.setLessonId(resultSet.getLong("lesson_id"));
                    testResponseForStudent.setTestId(resultSet.getLong("test_id"));
                    testResponseForStudent.setTestName(resultSet.getString("test_name"));
                    return testResponseForStudent;
                },
                testId
        );

        String questionSql = "SELECT q.id AS question_id, q.question_name, q.option_type " +
                "FROM questions q WHERE q.test_id = ?";
        assert testResponseForStudent1 != null;
        List<QuestionResponseForStudent> questionQuery = jdbcTemplate.query(questionSql,
                (resultSet, i) -> {
                    QuestionResponseForStudent questionResponse = new QuestionResponseForStudent();
                    questionResponse.setQuestionId(resultSet.getLong("question_id"));
                    questionResponse.setQuestionName(resultSet.getString("question_name"));
                    questionResponse.setOptionType(OptionType.valueOf(resultSet.getString("option_type")));
                    return questionResponse;
                },
                testResponseForStudent1.getTestId()
        );

        String optionSql = "SELECT o.id, o.text " +
                "FROM options o WHERE o.question_id = ?";
        List<QuestionResponseForStudent> questionResponses = jdbcTemplate.query(questionSql,
                (resultSet, i) -> {
                    QuestionResponseForStudent questionResponse = questionQuery.get(i);
                    List<OptionResponseForStudent> optionQuery = jdbcTemplate.query(optionSql,
                            (resulSet, j) -> new OptionResponseForStudent(
                                    resulSet.getLong("id"),
                                    resulSet.getString("text")
                            ),
                            questionResponse.getQuestionId()
                    );
                    questionResponse.setOptionResponses(optionQuery);
                    return questionResponse;
                }
        );

        testResponseForStudent1.setQuestionResponses(questionResponses);
        return testResponseForStudent1;

    }
}
