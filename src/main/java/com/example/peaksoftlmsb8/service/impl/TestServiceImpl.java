package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Option;
import com.example.peaksoftlmsb8.db.entity.Question;
import com.example.peaksoftlmsb8.db.entity.Test;
import com.example.peaksoftlmsb8.db.enums.OptionType;
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

    @Override
    public List<TestResponse> findAll() {
        List<Test> testList = testRepository.findAll();
        List<TestResponse> testResponses = new ArrayList<>();
        for (Test test : testList) {
            List<QuestionResponse> questionResponses = questionResponses(test.getQuestions());
            TestResponse testResponse = TestResponse.builder()
                    .LessonId(test.getLesson().getId())
                    .lessonName(test.getLesson().getName())
                    .testId(test.getId())
                    .testName(test.getName())
                    .dateTest(test.getDateTest())
                    .questionResponses(questionResponses)
                    .build();
            testResponses.add(testResponse);
        }
        return testResponses;
    }

    @Override
    public SimpleResponse saveTest(TestRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow(
                () -> new NotFoundException("Lesson with id : " + request.getLessonId() + " not found !")
        );
        if (lesson.getTest() == null) {
            Test test = new Test();
            int counter = 0;
            List<Question> questions = new ArrayList<>();
            for (QuestionRequest questionRequest : request.getQuestionRequests()) {
                Question question = Question.builder()
                        .questionName(questionRequest.getQuestionName())
                        .test(test)
                        .optionType(questionRequest.getOptionType())
                        .build();

                if (questionRequest.getOptionType().equals(OptionType.SINGLETON)) {
                    for (OptionRequest o : questionRequest.getOptionRequests()) {
                        Option option = Option.builder()
                                .text(o.getText())
                                .question(question)
                                .isTrue(o.getIsTrue())
                                .build();

                        question.addOption(option);

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
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Test with name: %s ", request.getTestName()))
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
        int counter = 0;
        for (QuestionUpdateRequest questionUpdateRequest : testUpdateRequest.getQuestionRequests()) {
            Question question = questionRepository.findById(questionUpdateRequest.getQuestionId()).orElseThrow(
                    () -> new NotFoundException("Question with id : " + questionUpdateRequest.getQuestionId() + "not found !"));

            for (OptionUpdateRequest optionUpdateRequest : questionUpdateRequest.getOptionRequests()) {
                if (questionUpdateRequest.getOptionType().equals(OptionType.SINGLETON)) {

                    if (optionUpdateRequest.getIsTrue().equals(true)) {
                        counter++;
                    }
                    if (counter < 1) {
                        throw new BadRequestException("You must choose one correct answer !");
                    }
                    if (counter > 1) {
                        throw new BadRequestException("You can only choose one correct answer !");
                    }
                } else {
                    if (optionUpdateRequest.getIsTrue().equals(true)) {
                        counter++;
                    }
                    if (counter < 1) {
                        throw new BadRequestException("You must choose one correct answer !");
                    }
                    if (counter > 2) {
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