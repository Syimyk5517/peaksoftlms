package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Option;
import com.example.peaksoftlmsb8.db.entity.Question;
import com.example.peaksoftlmsb8.db.entity.Test;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.*;
import com.example.peaksoftlmsb8.dto.response.OptionResponse;
import com.example.peaksoftlmsb8.dto.response.QuestionResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TestResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.OptionRepository;
import com.example.peaksoftlmsb8.repository.QuestionRepository;
import com.example.peaksoftlmsb8.repository.TestRepository;
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
        //QuestionResponses
        for (TestResponse testR : query) {
            String sql2 = """
                    select q.id as question_id,
                           q.question_name as question_name,
                           q.option_type as option_type
                    from questions q where q.test_id=? 
                    """;
            List<QuestionResponse> optionQuery = jdbcTemplate.query(sql2, (resultSet, i) -> {
                QuestionResponse questionResponse = new QuestionResponse();
                questionResponse.setQuestionId(resultSet.getLong("question_id"));
                questionResponse.setQuestionName(resultSet.getString("question_name"));
                questionResponse.setOptionType(OptionType.valueOf(resultSet.getString("option_type")));

                return questionResponse;
            }, testR.getTestId());
            testR.setQuestionResponses(optionQuery);
            testResponseList.add(testR);
        }
//              Option responses
        List<TestResponse> testResponses = new ArrayList<>();
        for (TestResponse test : testResponseList) {
            List<QuestionResponse> questionResponses = new ArrayList<>();
            for (QuestionResponse questionR : test.getQuestionResponses()) {
                String sql3 = """
                        select o.id as id,
                               o.text as text,
                               o.is_true as is_true
                        from options o where question_id=?
                        """;
                List<OptionResponse> optionQuery = jdbcTemplate.query(sql3, (resulSet, i) -> new OptionResponse(
                        resulSet.getLong("id"),
                        resulSet.getString("text"),
                        resulSet.getBoolean("is_true")
                ), questionR.getQuestionId());
                questionR.setOptionResponses(optionQuery);
                questionResponses.add(questionR);
            }
            test.setQuestionResponses(null);
            test.setQuestionResponses(questionResponses);
            testResponses.add(test);

        }
//            testResponse.setQuestionResponses(query2);
//            questionResponse.setOptionResponses(query1);

//        List<Test> testList = testRepository.findAll();
//        List<TestResponse> testResponses = new ArrayList<>();
//        for (Test test : testList) {
//            List<QuestionResponse> questionResponses = questionResponses(test.getQuestions());
//            TestResponse testResponse = TestResponse.builder()
//                    .LessonId(test.getLesson().getId())
//                    .lessonName(test.getLesson().getName())
//                    .testId(test.getId())
//                    .testName(test.getName())
//                    .dateTest(test.getDateTest())
//                    .questionResponses(questionResponses)
//                    .build();
//            testResponses.add(testResponse);
//        }
//        return testResponses;
        return testResponses;
    }


    @Override
    public SimpleResponse saveTest(TestRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow(
                () -> new NotFoundException("Lesson with id : " + request.getLessonId() + " not found !")
        );
        if (lesson.getTest() == null) {
            Test test = new Test();
            List<Question> questions = new ArrayList<>();
            for (QuestionRequest questionRequest : request.getQuestionRequests()) {
                Question question = Question.builder()
                        .questionName(questionRequest.getQuestionName())
                        .test(test)
                        .optionType(questionRequest.getOptionType())
                        .build();
                int counter = 0;

                if (questionRequest.getOptionType().equals(OptionType.SINGLETON)) {
                    for (OptionRequest o : questionRequest.getOptionRequests()) {
                        Option option = Option.builder()
                                .text(o.getText())
                                .question(question)
                                .isTrue(o.getIsTrue())
                                .build();

                        question.setOptions(List.of(option));

                        if (o.getIsTrue().equals(true)) {
                            ++counter;
                        }
                    }
                    if (counter < 1) {
                        throw new BadRequestException("You must choose one correct answer !");
                    }
                    if (counter > 1) {
                        throw new BadRequestException("You can only choose one correct answer !");
                    }
                } else {
                    for (OptionRequest optionRequest : questionRequest.getOptionRequests()) {
                        Option option = Option.builder()
                                .text(optionRequest.getText())
                                .question(question)
                                .isTrue(optionRequest.getIsTrue())
                                .build();

                        question.addOption(option);

                        if (optionRequest.getIsTrue().equals(true)) {
                            counter++;
                        }
                        if (counter < 1) {
                            throw new BadRequestException("You must choose one correct answer !");
                        }
                        if (counter > 2) {
                            throw new BadRequestException("You can only choose two correct answers !");
                        }

                    }
                }
                questions.add(question);
            }
            test.setDateTest(LocalDate.now());
            test.setLesson(lesson);
            test.setQuestions(questions);
            testRepository.save(test);
        } else {
            throw new AlReadyExistException("Uje v etom lessone task ect");
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Test with name: %s successfully saved !", request.getTestName()))
                .build();
    }

    @Override
    public TestResponse findById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test with id : " + id + " not found !"));
        List<QuestionResponse> questionResponses = questionResponses(test.getQuestions());
        return TestResponse.builder()
                .LessonId(test.getLesson().getId())
                .lessonName(test.getLesson().getName())
                .testId(test.getId())
                .testName(test.getName())
                .dateTest(test.getDateTest())
                .questionResponses(questionResponses)
                .build();

    }

    @Override
    public SimpleResponse updateTest(Long testId, TestUpdateRequest testUpdateRequest) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> new NotFoundException("Test with id : " + testId + " not found !"));

        Lesson lesson = lessonRepository.findById(testUpdateRequest.getLessonId()).orElseThrow(
                () -> new NotFoundException("Lesson with id : " + testUpdateRequest.getLessonId() + " not found!")
        );

        List<Question> questions = new ArrayList<>();
        for (QuestionUpdateRequest questionUpdateRequest : testUpdateRequest.getQuestionRequests()) {
            Question question = questionRepository.findById(questionUpdateRequest.getQuestionId()).orElseThrow(
                    () -> new NotFoundException("Question with id : " + questionUpdateRequest.getQuestionId() + "not found !"));
            int counter1 = 0;
            if (question.getOptionType().equals(questionUpdateRequest.getOptionType()) && question.getQuestionName().equals(questionUpdateRequest.getQuestionName())) {
                counter1++;
            }
            int counterIsTrue = 0;

            for (OptionUpdateRequest optionUpdateRequest : questionUpdateRequest.getOptionRequests()) {
                if (questionUpdateRequest.getOptionType().equals(OptionType.SINGLETON)) {
                    if (counter1 > 0) {
                        throw new BadRequestException("SKJA");
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
                Option option = optionRepository.findById(optionUpdateRequest.getOptionId()).orElseThrow(
                        () -> new NotFoundException("Option with id : " + optionUpdateRequest.getOptionId() + "not found !"));
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
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Test with name : %s successfully updated !", test.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> new NotFoundException("Test with id : " + testId + " not found !"));
        testRepository.delete(test);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Test with id  : %s successfully deleted !", testId))
                .build();
    }

    private List<QuestionResponse> questionResponses(List<Question> questions) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question q : questions) {
            QuestionResponse questionResponse = QuestionResponse.builder()
                    .questionId(q.getId())
                    .questionName(q.getQuestionName())
                    .build();
            for (Option option : q.getOptions()) {
                OptionResponse optionResponse = OptionResponse.builder()
                        .optionId(option.getId())
                        .isTrue(option.getIsTrue())
                        .text(option.getText())
                        .build();
                questionResponse.addOption(optionResponse);
            }
            questionResponses.add(questionResponse);

        }
        return questionResponses;
    }
}