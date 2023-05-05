package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.*;
import com.example.peaksoftlmsb8.dto.response.test.*;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.*;
import com.example.peaksoftlmsb8.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ResultOfTestRepository resultOfTestRepository;

    @Override
    public List<TestResponse> findAll() {
        List<TestResponse> testResponseList = new ArrayList<>();
        String sql = """
                select l.id as lesson_id ,
                       l.name as lesson_name,
                       t.id as test_id,
                       t.name as test_name,
                       t.date_test as date_test
                       from lessons l join tests t on l.id = t.lesson_id
                """;
        List<TestResponse> query = jdbcTemplate.query(sql, (resulSet, i) -> {
            TestResponse testResponse = new TestResponse();
            testResponse.setLessonId(resulSet.getLong("lesson_id"));
            testResponse.setLessonName(resulSet.getString("lesson_name"));
            testResponse.setTestId(resulSet.getLong("test_id"));
            testResponse.setTestName(resulSet.getString("test_name"));
            testResponse.setDateTest(resulSet.getDate("date_test").toLocalDate());
            return testResponse;
        });

        for (TestResponse testR : query) {
            String questionSql = """
                    select q.id as question_id,
                           q.question_name as question_name,
                           q.option_type as option_type
                    from questions q where q.test_id=?
                    """;
            List<QuestionResponse> questionQuery = jdbcTemplate.query(questionSql, (resultSet, i) -> {
                QuestionResponse questionResponse = new QuestionResponse();
                questionResponse.setQuestionId(resultSet.getLong("question_id"));
                questionResponse.setQuestionName(resultSet.getString("question_name"));
                questionResponse.setOptionType(OptionType.valueOf(resultSet.getString("option_type")));

                return questionResponse;
            }, testR.getTestId());
            testR.setQuestionResponses(questionQuery);
            testResponseList.add(testR);
        }

        List<TestResponse> testResponses = new ArrayList<>();
        for (TestResponse test : testResponseList) {
            List<QuestionResponse> questionResponses = new ArrayList<>();
            for (QuestionResponse questionR : test.getQuestionResponses()) {
                String optionSql = """
                        select o.id as id,
                               o.text as text,
                               o.is_true as is_true
                        from options o where question_id=?
                        """;
                List<OptionResponse> optionQuery = jdbcTemplate.query(optionSql, (resulSet, i) ->
                                new OptionResponse(resulSet.getLong("id"),
                                        resulSet.getString("text"),
                                        resulSet.getBoolean("is_true")),
                        questionR.getQuestionId());
                questionR.setOptionResponses(optionQuery);
                questionResponses.add(questionR);
            }
            test.setQuestionResponses(null);
            test.setQuestionResponses(questionResponses);
            testResponses.add(test);

        }
        return testResponses;
    }


    @Override
    public SimpleResponse saveTest(TestRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow(() -> new NotFoundException("Lesson with id : " + request.getLessonId() + " not found !"));
        if (lesson.getTest() == null) {
            Test test = new Test();
            List<Question> questions = new ArrayList<>();
            for (QuestionRequest questionRequest : request.getQuestionRequests()) {
                Question question = Question.builder().questionName(questionRequest.getQuestionName()).test(test).optionType(questionRequest.getOptionType()).build();
                int counter = 0;

                for (OptionRequest o : questionRequest.getOptionRequests()) {
                    if (questionRequest.getOptionType().equals(OptionType.SINGLETON)) {

                        if (o.getIsTrue().equals(true)) {
                            ++counter;
                        }
                        if (counter < 1) {
                            throw new BadRequestException("You must choose one correct answer !");
                        }
                        if (counter > 1) {
                            throw new BadRequestException("You can only choose one correct answer !");
                        }
                    } else {

                        if (o.getIsTrue().equals(true)) {
                            counter++;
                        }
                        if (counter < 1) {
                            throw new BadRequestException("You must choose one correct answer !");
                        }
                        if (counter > 2) {
                            throw new BadRequestException("You can only choose two correct answers !");
                        }

                    }
                    Option option = Option.builder().text(o.getText()).question(question).isTrue(o.getIsTrue()).build();
                    question.setOptions(List.of(option));
                }
                questions.add(question);
            }
            test.setDateTest(LocalDate.now());
            test.setLesson(lesson);
            test.setQuestions(questions);
            testRepository.save(test);
        } else {
            throw new AlReadyExistException("There is already a test in this lesson !");
        }

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Test with name: %s successfully saved !", request.getTestName())).build();
    }

    @Override
    public TestResponse findById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException("Test with id : " + id + " not found !"));
        List<QuestionResponse> questionResponses = questionResponses(test.getQuestions());
        return TestResponse.builder().lessonId(test.getLesson().getId()).lessonName(test.getLesson().getName()).testId(test.getId()).testName(test.getName()).dateTest(test.getDateTest()).questionResponses(questionResponses).build();

    }

    @Override
    public SimpleResponse updateTest(Long testId, TestUpdateRequest testUpdateRequest) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test with id : " + testId + " not found !"));

        Lesson lesson = lessonRepository.findById(testUpdateRequest.getLessonId()).orElseThrow(() -> new NotFoundException("Lesson with id : " + testUpdateRequest.getLessonId() + " not found!"));

        List<Question> questions = new ArrayList<>();
        for (QuestionUpdateRequest questionUpdateRequest : testUpdateRequest.getQuestionRequests()) {
            Question question = questionRepository.findById(questionUpdateRequest.getQuestionId()).orElseThrow(() -> new NotFoundException("Question with id : " + questionUpdateRequest.getQuestionId() + "not found !"));
            question.setTest(test);
            question.setQuestionName(questionUpdateRequest.getQuestionName());
            question.setOptionType(questionUpdateRequest.getOptionType());
            int counter1 = 0;
            if (question.getOptionType().equals(questionUpdateRequest.getOptionType()) && question.getQuestionName().equals(questionUpdateRequest.getQuestionName())) {
                counter1++;
            }
            int counterIsTrue = 0;

            for (OptionUpdateRequest optionUpdateRequest : questionUpdateRequest.getOptionRequests()) {
                if (questionUpdateRequest.getOptionType().equals(OptionType.SINGLETON)) {
                    if (counter1 > 0) {
                        throw new BadRequestException("You can't change the options until the question is changed");
                    }

                    if (optionUpdateRequest.getIsTrue().equals(true)) {
                        counterIsTrue++;
                    }
                    if (counterIsTrue < 1) {
                        throw new BadRequestException("You must choose one correct answer !");
                    }
                    if (counterIsTrue > 1) {
                        throw new BadRequestException("You can only choose one correct answer !");
                    }
                } else {
                    if (optionUpdateRequest.getIsTrue().equals(true)) {
                        counterIsTrue++;
                    }
                    if (counterIsTrue < 1) {
                        throw new BadRequestException("You must choose one correct answer !");
                    }
                    if (counterIsTrue > 2) {
                        throw new BadRequestException("You can only choose two correct answers !");
                    }

                }
                Option option = optionRepository.findById(optionUpdateRequest.getOptionId()).orElseThrow(() -> new NotFoundException("Option with id : " + optionUpdateRequest.getOptionId() + "not found !"));
                option.setText(optionUpdateRequest.getText());
                option.setQuestion(question);
                option.setIsTrue(optionUpdateRequest.getIsTrue());
                question.addOption(option);
            }

            questions.add(question);
        }
        test.setName(testUpdateRequest.getTestName());
        test.setLesson(lesson);
        test.setQuestions(questions);
        testRepository.save(test);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Test with name : %s successfully updated !", test.getName())).build();
    }

    @Override
    public SimpleResponse deleteById(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test with id : " + testId + " not found !"));
        ResultOfTest result = resultOfTestRepository.findResultOfTestById(test.getId()).orElseThrow(
                () -> new NotFoundException("Test with id:"+ testId + "not found"));
        resultOfTestRepository.delete(result);
        testRepository.delete(test);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Test with id  : %s successfully deleted !", testId)).build();
    }

    @Override
    public TestResponseForStudent findByTestById(Long testId) {
        String testSql = """
                select t.lesson_id as lesson_id ,
                       t.name as lesson_name,
                       t.id as test_id,
                       t.name as test_name,
                       t.date_test as date_test
                       from tests t where t.id = ?
                """;
        TestResponseForStudent testResponseForStudent1 = jdbcTemplate.query(testSql, (resulSet, i) -> {
            TestResponseForStudent testResponseForStudent = new TestResponseForStudent();
            testResponseForStudent.setLessonId(resulSet.getLong("lesson_id"));
            testResponseForStudent.setTestId(resulSet.getLong("test_id"));
            testResponseForStudent.setTestName(resulSet.getString("test_name"));
            return testResponseForStudent;
        }, testId).stream().findAny().orElseThrow(() -> new NotFoundException("NOt found"));


        String questionSql = """
                select q.id as question_id,
                       q.question_name as question_name,
                       q.option_type as option_type
                from questions q where q.test_id=?
                """;
        List<QuestionResponseForStudent> questionQuery = jdbcTemplate.query(questionSql, (resultSet, i) -> {
            QuestionResponseForStudent questionResponse = new QuestionResponseForStudent();
            questionResponse.setQuestionId(resultSet.getLong("question_id"));
            questionResponse.setQuestionName(resultSet.getString("question_name"));
            questionResponse.setOptionType(OptionType.valueOf(resultSet.getString("option_type")));

            return questionResponse;
        }, testResponseForStudent1.getTestId());

        List<QuestionResponseForStudent> questionResponses = new ArrayList<>();
        for (QuestionResponseForStudent questionR : questionQuery) {
            String optionSql = """
                    select o.id as id,
                           o.text as text
                    from options o where question_id=?
                    """;
            List<OptionResponseForStudent> optionQuery = jdbcTemplate.query(optionSql, (resulSet, i) ->
                            new OptionResponseForStudent(resulSet.getLong("id"),
                                    resulSet.getString("text")),
                    questionR.getQuestionId());
            questionR.setOptionResponses(optionQuery);
            questionResponses.add(questionR);
        }

        testResponseForStudent1.setQuestionResponses(questionResponses);
        return testResponseForStudent1;
    }

    private List<QuestionResponse> questionResponses(List<Question> questions) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question q : questions) {
            QuestionResponse questionResponse = QuestionResponse.builder().questionId(q.getId()).questionName(q.getQuestionName()).build();
            for (Option option : q.getOptions()) {
                OptionResponse optionResponse = OptionResponse.builder().optionId(option.getId()).isTrue(option.getIsTrue()).text(option.getText()).build();
                questionResponse.addOption(optionResponse);
            }
            questionResponses.add(questionResponse);

        }
        return questionResponses;
    }
}