package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.dto.request.test.TestRequest;
import com.example.peaksoftlmsb8.dto.request.test.TestUpdateRequest;
import com.example.peaksoftlmsb8.dto.request.test.option.OptionRequest;
import com.example.peaksoftlmsb8.dto.request.test.option.OptionUpdateRequest;
import com.example.peaksoftlmsb8.dto.request.test.question.QuestionRequest;
import com.example.peaksoftlmsb8.dto.request.test.question.QuestionUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.test.OptionResponse;
import com.example.peaksoftlmsb8.dto.response.test.QuestionResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponseForInstructor;
import com.example.peaksoftlmsb8.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.exception.BadRequestException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.repository.*;
import com.example.peaksoftlmsb8.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
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

    private final ResultOfTestRepository resultOfTestRepository;
    private final JwtService jwtService;
    private final TestRepo testRepo;
    private static final Logger logger = LogManager.getLogger(TestServiceImpl.class);

    @Override
    public List<TestResponseForInstructor> findAll() {
        return testRepo.findAll();
    }


    @Override
    public SimpleResponse saveTest(TestRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> {
                    logger.error("Lesson with id: " + request.getLessonId() + " not found!");
                    throw new NotFoundException("Урок с идентификатором: " + request.getLessonId() + " не найден !");
                });

        if (lesson.getTest() != null) {
            logger.error("There is already a test in this lesson!");
            throw new AlReadyExistException("В этом уроке уже есть тест!");
        }

        Test test = new Test();
        test.setDateTest(LocalDate.now());
        test.setLesson(lesson);

        List<Question> questions = new ArrayList<>();
        for (QuestionRequest questionRequest : request.getQuestionRequests()) {
            if (questionRequest.getOptionRequests().isEmpty()) {
                logger.error("You created a question but did not write answers");
                throw new BadRequestException("Вы создали вопрос, но не написали ответы");
            }

            int counter = 0;
            Question question = Question.builder()
                    .questionName(questionRequest.getQuestionName())
                    .test(test)
                    .optionType(questionRequest.getOptionType())
                    .build();

            for (OptionRequest o : questionRequest.getOptionRequests()) {
                if (questionRequest.getOptionType().equals(OptionType.SINGLETON)) {
                    if (o.getIsTrue().equals(true)) {
                        ++counter;
                    }
                    if (counter < 1) {
                        logger.error("You must choose one correct answer!");
                        throw new BadRequestException("Вы должны выбрать один правильный ответ!");
                    }
                    if (counter > 1) {
                        logger.error("You must choose one correct answer!");
                        throw new BadRequestException("Вы можете выбрать только один правильный ответ!");
                    }
                } else {
                    if (o.getIsTrue().equals(true)) {
                        counter++;
                    }
                    if (counter < 1) {
                        logger.error("You must choose one correct answer!");
                        throw new BadRequestException("Вы должны выбрать один правильный ответ!");
                    }
                    if (counter > 2) {
                        logger.error("You must choose two correct answers!");
                        throw new BadRequestException("Вы можете выбрать только два правильных ответа!");
                    }
                }

                Option option = Option.builder()
                        .text(o.getText())
                        .question(question)
                        .isTrue(o.getIsTrue())
                        .build();
                question.getOptions().add(option);
            }

            questions.add(question);
        }

        test.setQuestions(questions);
        testRepository.save(test);

        logger.info("Test with name: " + request.getTestName() + " successfully saved!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Тест с именем: %s успешно сохранен!", request.getTestName()))
                .build();
    }


    @Override
    public SimpleResponse updateTest(TestUpdateRequest testUpdateRequest) {
        Test test = testRepository.findById(testUpdateRequest.getTestId()).orElseThrow(
                () -> {
                    logger.error("Test with id: " + testUpdateRequest.getTestId() + " not found!");
                    throw new NotFoundException("Тест с идентификатором: " + testUpdateRequest.getTestId() + " не найден!");
                });

        Lesson lesson = lessonRepository.findById(testUpdateRequest.getLessonId()).orElseThrow(
                () -> {
                    logger.error("Lesson with id: " + testUpdateRequest.getTestId() + " not found!");
                    throw new NotFoundException("Урок с идентификатором: " + testUpdateRequest.getLessonId() + " не найден!");
                });

        if (!testUpdateRequest.getDeleteQuestionsIds().isEmpty()) {
            for (Long questionId : testUpdateRequest.getDeleteQuestionsIds()) {
                Question question = questionRepository.findById(questionId).orElseThrow(
                        () -> {
                            logger.error("Question with id: " + questionId + " not found!");
                            throw new NotFoundException("Вопрос с id: " + questionId + " не найден !");
                        });
                questionRepository.delete(question);
            }
        }
        if (!testUpdateRequest.getDeleteOptionsIds().isEmpty()) {
            for (Long optionId : testUpdateRequest.getDeleteOptionsIds()) {
                Option option = optionRepository.findById(optionId).orElseThrow(
                        () -> {
                            logger.error("Option with id: " + optionId + " not found!");
                            throw new NotFoundException("Вариант с идентификатором: " + optionId + " не найден!");
                        });
                optionRepository.delete(option);
            }
        }

        List<Question> questions = new ArrayList<>();
        for (QuestionUpdateRequest questionUpdateRequest : testUpdateRequest.getQuestionRequests()) {
            Question question = questionRepository.findById(questionUpdateRequest.getQuestionId()).orElseThrow(
                    () -> {
                        logger.error("Question with id: " + questionUpdateRequest.getQuestionId() + " not found!");
                        throw new NotFoundException("Вопрос с идентификатором: " + questionUpdateRequest.getQuestionId() + " не найден!");
                    });
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
                        () -> {
                            logger.error("Option with id: " + optionUpdateRequest.getOptionId() + " not found!");
                            throw new NotFoundException("Вариант с идентификатором: " + optionUpdateRequest.getOptionId() + " не найден!");
                        });
                if (optionUpdateRequest.getText() != null) {
                    option.setText(optionUpdateRequest.getText());
                }
                if (optionUpdateRequest.getIsTrue() != null) {
                    option.setIsTrue(optionUpdateRequest.getIsTrue());
                }
                option.setQuestion(question);
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
                .message(String.format("Тест с именем: %s успешно обновлен!", test.getName()))
                .build();
    }


    @Override
    public SimpleResponse deleteById(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> {
                    logger.error("Test with id:" + testId + "not found");
                    throw new NotFoundException("Тест с идентификатором: " + testId + " не найден!");
                });
        ResultOfTest result = resultOfTestRepository.findResultOfTestById(test.getId()).orElseThrow(
                () -> {
                    logger.error("Result with id:" + testId + "not found");
                    throw new NotFoundException("Результат теста с идентификатором: " + testId + " не найден!");
                });
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
        if (accountInToken.getRole().equals(Role.INSTRUCTOR)) {
            Test test = testRepository.findById(testId).orElseThrow(
                    () -> {
                        logger.error("Result with id:" + testId + "not found");
                        throw new NotFoundException("Тест с идентификатором: " + testId + " не найден!");
                    });
            List<QuestionResponse> questionResponses = new ArrayList<>();
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
            return TestResponseForInstructor.builder()
                    .lessonId(test.getLesson().getId())
                    .lessonName(test.getLesson().getName())
                    .testId(test.getId())
                    .testName(test.getName())
                    .dateTest(test.getDateTest())
                    .questionResponses(questionResponses)
                    .build();

        } else {
            return testRepo.findById(testId);
        }
    }


}
