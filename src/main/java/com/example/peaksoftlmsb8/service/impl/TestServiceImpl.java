package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Option;
import com.example.peaksoftlmsb8.db.entity.Question;
import com.example.peaksoftlmsb8.db.entity.Test;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.OptionRequest;
import com.example.peaksoftlmsb8.dto.request.QuestionRequest;
import com.example.peaksoftlmsb8.dto.request.TestRequest;
import com.example.peaksoftlmsb8.dto.response.OptionResponse;
import com.example.peaksoftlmsb8.dto.response.QuestionResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TestResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.TestRepository;
import com.example.peaksoftlmsb8.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;

    @Override
    public List<TestResponse> findAll() {
        List<Test> testList = testRepository.findAll();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        List<TestResponse> testResponses = new ArrayList<>();
        for (Test test : testList) {
            for (Question question : test.getQuestions()) {
                QuestionResponse questionResponse = QuestionResponse.builder()
                        .questionId(question.getId())
                        .questionName(question.getQuestionName())
                        .build();
                for (Option option : question.getOptions()) {
                    OptionResponse optionResponse = OptionResponse.builder()
                            .optionId(option.getId())
                            .isTrue(option.getIsTrue())
                            .text(option.getText())
                            .build();
                    questionResponse.addOption(optionResponse);
                }
                questionResponses.add(questionResponse);
            }
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
                () -> new NotFoundException("Lesson with id: " + " not found!")
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
                            counter++;
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