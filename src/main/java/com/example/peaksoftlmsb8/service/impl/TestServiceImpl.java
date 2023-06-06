package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.test.*;
import com.example.peaksoftlmsb8.dto.request.test.option.OptionRequest;
import com.example.peaksoftlmsb8.dto.request.test.option.OptionUpdateRequest;
import com.example.peaksoftlmsb8.dto.request.test.question.QuestionRequest;
import com.example.peaksoftlmsb8.dto.request.test.question.QuestionUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.test.*;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.*;
import com.example.peaksoftlmsb8.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@Log4j2
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ResultOfTestRepository resultOfTestRepository;
    private final JwtService jwtService;
    private static final Logger logger = LogManager.getLogger(TestServiceImpl.class);

    @Override
    public List<TestResponseForInstructor> findAll() {
        List<TestResponseForInstructor> testResponseList = new ArrayList<>();
        String sql = """
                select l.id as lesson_id ,
                       l.name as lesson_name,
                       t.id as test_id,
                       t.name as test_name,
                       t.date_test as date_test
                       from lessons l join tests t on l.id = t.lesson_id
                """;
        List<TestResponseForInstructor> query = jdbcTemplate.query(sql, (resulSet, i) -> {
            TestResponseForInstructor testResponse = new TestResponseForInstructor();
            testResponse.setLessonId(resulSet.getLong("lesson_id"));
            testResponse.setLessonName(resulSet.getString("lesson_name"));
            testResponse.setTestId(resulSet.getLong("test_id"));
            testResponse.setTestName(resulSet.getString("test_name"));
            testResponse.setDateTest(resulSet.getDate("date_test").toLocalDate());
            return testResponse;
        });

        for (TestResponseForInstructor testR : query) {
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

        List<TestResponseForInstructor> testResponses = new ArrayList<>();
        for (TestResponseForInstructor test : testResponseList) {
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
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow(
                () -> new NotFoundException("Урок с идентификатором: " + request.getLessonId() + " не найден !"));
        if (lesson.getTest() == null) {
            Test test = new Test();
            List<Question> questions = questionList(request.getQuestionRequests(), test);
            test.setDateTest(LocalDate.now());
            test.setLesson(lesson);
            test.setQuestions(questions);
            testRepository.save(test);
        } else {
            logger.info("There is already a test in this lesson !");
            throw new AlReadyExistException("В этом уроке уже есть тест!");
        }
        logger.info("Test with name: " + request.getTestName() + " successfully saved !");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Тест с именем: %s успешно сохранен!", request.getTestName())).build();
    }


    @Override
    public SimpleResponse updateTest(TestUpdateRequest testUpdateRequest) {
        Test test = testRepository.findById(testUpdateRequest.getTestId()).orElseThrow(
                () -> new NotFoundException("Тест с идентификатором: " + testUpdateRequest.getTestId() + " не найден!"));

        Lesson lesson = lessonRepository.findById(testUpdateRequest.getLessonId()).orElseThrow(
                () -> new NotFoundException("Урок с идентификатором: " + testUpdateRequest.getLessonId() + " не найден!"));

        if (!testUpdateRequest.getDeleteQuestionsIds().isEmpty()) {
            for (Long questionId : testUpdateRequest.getDeleteQuestionsIds()) {
                Question question = questionRepository.findById(questionId).orElseThrow(
                        () -> new NotFoundException("Вопрос с id: " + questionId + " не найден !"));
                questionRepository.delete(question);
            }
        }
        if (!testUpdateRequest.getDeleteOptionsIds().isEmpty()) {
            for (Long optionId : testUpdateRequest.getDeleteOptionsIds()) {
                Option option = optionRepository.findById(optionId).orElseThrow(
                        () -> new NotFoundException("Вариант с идентификатором: " + optionId + " не найден!"));
                optionRepository.delete(option);
            }
        }

        List<Question> questions = new ArrayList<>();
        for (QuestionUpdateRequest questionUpdateRequest : testUpdateRequest.getQuestionRequests()) {

            Question question = questionRepository.findById(questionUpdateRequest.getQuestionId()).orElseThrow(
                    () -> new NotFoundException("Вопрос с идентификатором: " + questionUpdateRequest.getQuestionId() + " не найден!"));
            question.setTest(test);

            if (questionUpdateRequest.getQuestionName() != null) {
                question.setQuestionName(questionUpdateRequest.getQuestionName());
            }
            if (questionUpdateRequest.getOptionType() != null) {
                question.setOptionType(questionUpdateRequest.getOptionType());
            }
            int counterIsTrue = 0;

            for (OptionUpdateRequest optionUpdateRequest : questionUpdateRequest.getOptionRequests()) {
                if (questionUpdateRequest.getOptionType() == OptionType.SINGLETON) {

                    if (optionUpdateRequest.getIsTrue().equals(true)) {
                        counterIsTrue++;
                    }
                    if (counterIsTrue < 1) {
                        logger.info("You must choose one correct answer !");
                        throw new BadRequestException("Вы должны выбрать один правильный ответ!");
                    }
                    if (counterIsTrue > 1) {
                        logger.info("You can only choose one correct answer !");
                        throw new BadRequestException("Вы можете выбрать только один правильный ответ!");
                    }
                } else {
                    if (optionUpdateRequest.getIsTrue().equals(true)) {
                        counterIsTrue++;
                    }
                    if (counterIsTrue < 1) {
                        logger.info("You must choose one correct answer !");
                        throw new BadRequestException("Вы должны выбрать один правильный ответ!");
                    }
                    if (counterIsTrue > 2) {
                        logger.info("You can only choose two correct answers !");
                        throw new BadRequestException("Вы можете выбрать только два правильных ответа!");
                    }

                }
                Option option = optionRepository.findById(optionUpdateRequest.getOptionId()).orElseThrow(
                        () -> new NotFoundException("Вариант с идентификатором: " + optionUpdateRequest.getOptionId() + " не найден!"));
                if (optionUpdateRequest.getText() != null) {
                    option.setText(optionUpdateRequest.getText());
                }
                if (optionUpdateRequest.getIsTrue() != null) {
                    option.setIsTrue(optionUpdateRequest.getIsTrue());
                }
                option.setQuestion(question);
                question.addOption(option);
            }
        }
        if (!testUpdateRequest.getQuestionRequests().isEmpty()) {
            List<Question> questionList = questionList(testUpdateRequest.getQuestionRequestList(), test);
            questions.addAll(questionList);
        }
        test.setName(testUpdateRequest.getTestName());
        test.setLesson(lesson);
        test.setQuestions(questions);
        testRepository.save(test);
        return SimpleResponse.builder().
                httpStatus(HttpStatus.OK).
                message(String.format("Тест с именем: %s успешно обновлен!", test.getName())).build();
    }

    @Override
    public SimpleResponse deleteById(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> new NotFoundException("Тест с идентификатором: " + testId + " не найден!"));
        logger.info("Test with id:" + testId + "not found");
        ResultOfTest result = resultOfTestRepository.findResultOfTestById(test.getId()).orElseThrow(
                () -> new NotFoundException("Результат теста с идентификатором: " + testId + " не найден!"));
        resultOfTestRepository.delete(result);
        testRepository.delete(test);
        logger.info("Test with id  : " + testId + " successfully deleted !");
        return SimpleResponse.builder().
                httpStatus(HttpStatus.OK).
                message(String.format("Тест с идентификатором: %s успешно удален!", testId)).build();

    }

    @Override
    public TestResponse findById(Long testId) {
        User accountInToken = jwtService.getAccountInToken();
        if (accountInToken.getRole().equals(Role.STUDENT)) {
            Test test = testRepository.findById(testId).orElseThrow(
                    () -> new NotFoundException("Тест с идентификатором: " + testId + " не найден!"));
            List<QuestionResponse> questionResponses = questionResponses(test.getQuestions());
            return TestResponseForInstructor.builder().
                    lessonId(test.getLesson().getId()).
                    lessonName(test.getLesson().getName()).
                    testId(test.getId()).
                    testName(test.getName()).
                    dateTest(test.getDateTest()).
                    questionResponses(questionResponses).
                    build();

        } else {
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

    private List<Question> questionList(List<QuestionRequest> questionRequests, Test test) {
        List<Question> questions = new ArrayList<>();
        for (QuestionRequest questionRequest : questionRequests) {
            Question question = Question.builder().
                    questionName(questionRequest.getQuestionName()).
                    test(test).
                    optionType(questionRequest.getOptionType()).build();
            int counter = 0;
            if (questionRequest.getOptionRequests().isEmpty()) {
                throw new BadRequestException("Вы создали вопрос, но не написали ответы");
            }
            for (OptionRequest o : questionRequest.getOptionRequests()) {
                if (questionRequest.getOptionType().equals(OptionType.SINGLETON)) {

                    if (o.getIsTrue().equals(true)) {
                        ++counter;
                    }
                    if (counter < 1) {
                        throw new BadRequestException("Вы должны выбрать один правильный ответ!");
                    }
                    if (counter > 1) {
                        throw new BadRequestException("Вы можете выбрать только один правильный ответ!");
                    }
                } else {

                    if (o.getIsTrue().equals(true)) {
                        counter++;
                    }
                    if (counter < 1) {
                        throw new BadRequestException("Вы должны выбрать один правильный ответ!");
                    }
                    if (counter > 2) {
                        throw new BadRequestException("Вы можете выбрать только два правильных ответа!");
                    }

                }
                Option option = Option.builder().text(o.getText()).question(question).isTrue(o.getIsTrue()).build();
                question.setOptions(List.of(option));
            }
            questions.add(question);
        }
        return questions;
    }
}
