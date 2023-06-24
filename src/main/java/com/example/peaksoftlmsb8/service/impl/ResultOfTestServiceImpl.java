package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassQuestionRequest;
import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassTestRequest;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForStudent;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOptionResponse;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultQuestionResponse;
import com.example.peaksoftlmsb8.repository.*;
import com.example.peaksoftlmsb8.service.ResultOfTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ResultOfTestServiceImpl implements ResultOfTestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final ResultOfTestRepository resultOfTestRepository;
    private final ResultRepository resultRepository;
    private final JwtService jwtService;
    private static final Logger logger = LogManager.getLogger(ResultOfTestServiceImpl.class);


    @Override
    public ResultOfTestResponseForStudent findResultOfTestByTestIdForStudent(Long testId) {
        User accountInToken = jwtService.getAccountInToken();
        Student student = accountInToken.getStudent();

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> {
                    String errorMessage = "Test with id: " + testId + " not found!";
                    logger.error(errorMessage);
                    throw new NotFoundException(errorMessage);
                });

        ResultOfTest resultOfTest = resultOfTestRepository.findByStudentId(student.getId())
                .orElseThrow(() -> {
                    String errorMessage = "Result of test with id " + student.getId() + " not found";
                    logger.error(errorMessage);
                    throw new NotFoundException(errorMessage);
                });

        if (!resultOfTest.getTest().getId().equals(test.getId())) {
            return null;
        }

        List<ResultQuestionResponse> resultQuestionResponses = new ArrayList<>();
        int countCorrectStudentAnswers = 0;

        for (Question question : test.getQuestions()) {
            ResultQuestionResponse questionResponse = new ResultQuestionResponse();
            questionResponse.setQuestionId(question.getId());
            questionResponse.setQuestionName(question.getQuestionName());
            questionResponse.setOptionType(question.getOptionType());

            List<ResultOptionResponse> optionResponses = new ArrayList<>();

            for (Option option : question.getOptions()) {
                ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
                resultOptionResponse.setOptionId(option.getId());
                resultOptionResponse.setText(option.getText());
                resultOptionResponse.setIsTrue(option.getIsTrue());
                optionResponses.add(resultOptionResponse);

                if (resultOfTest.getStudentAnswers().contains(option.getId())) {
                    int pointsToAdd = (question.getOptionType() == OptionType.SINGLETON) ? 10 : 5;
                    questionResponse.setPoint(questionResponse.getPoint() + pointsToAdd);
                    questionResponse.getStudentAnswers().add(option.getId());
                    countCorrectStudentAnswers += pointsToAdd;
                }
            }

            questionResponse.setOptionResponses(optionResponses);
            resultQuestionResponses.add(questionResponse);
        }

        return ResultOfTestResponseForStudent.builder()
                .resultOfTestId(resultOfTest.getId())
                .testId(test.getId())
                .testName(test.getName())
                .resultQuestionResponses(resultQuestionResponses)
                .studentPoint(countCorrectStudentAnswers)
                .build();

    }

    @Override
    public ResultOfTestResponseForStudent passTest(PassTestRequest passTestRequest) {
        User accountInToken = jwtService.getAccountInToken();
        Student student = accountInToken.getStudent();
        Test test = testRepository.findById(passTestRequest.getTestId())
                .orElseThrow(() -> new NotFoundException("Тест с идентификатором: " + passTestRequest.getTestId() + " не найден!"));

        ResultOfTest resultOfTest = new ResultOfTest();
        List<ResultQuestionResponse> resultQuestionResponses = new ArrayList<>();
        int countCorrectAnswer = 0;
        int countCorrect = 0;
        int countInCorrect = 0;

        for (PassQuestionRequest questionRequest : passTestRequest.getPassQuestionRequest()) {
            Question question = questionRepository.findById(questionRequest.getQuestionId())
                    .orElseThrow(() -> new NotFoundException("Вопрос с идентификатором: " + questionRequest.getQuestionId() + " не найден"));

            ResultQuestionResponse questionResponse = new ResultQuestionResponse();
            questionResponse.setQuestionId(question.getId());
            questionResponse.setQuestionName(question.getQuestionName());
            questionResponse.setOptionType(question.getOptionType());
            questionResponse.setStudentAnswers(questionRequest.getOptionId());

            resultOfTest.getStudentAnswers().addAll(questionRequest.getOptionId());

            List<ResultOptionResponse> optionResponses = new ArrayList<>();

            for (Option option : question.getOptions()) {
                ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
                resultOptionResponse.setOptionId(option.getId());
                resultOptionResponse.setText(option.getText());
                resultOptionResponse.setIsTrue(option.getIsTrue());
                optionResponses.add(resultOptionResponse);

                if (questionRequest.getOptionId().contains(option.getId()) && option.getIsTrue().equals(true)) {
                    int pointsToAdd = (question.getOptionType() == OptionType.SINGLETON) ? 10 : 5;
                    questionResponse.setPoint(questionResponse.getPoint() + pointsToAdd);
                    countCorrect++;
                    countCorrectAnswer += pointsToAdd;
                }

            }

            if (questionResponse.getPoint() == 0) {
                countInCorrect++;
            }

            questionResponse.setOptionResponses(optionResponses);
            resultQuestionResponses.add(questionResponse);
        }

        resultOfTest.setTest(test);
        resultOfTest.setStudent(student);
        resultOfTest.setCountCorrect(countCorrect);
        resultOfTest.setCountInCorrect(countInCorrect);
        resultOfTestRepository.save(resultOfTest);

        return ResultOfTestResponseForStudent.builder()
                .testId(test.getId())
                .testName(test.getName())
                .resultQuestionResponses(resultQuestionResponses)
                .studentPoint(countCorrectAnswer)
                .build();

    }

    @Override
    public List<ResultOfTestResponseForInstructor> findAll(Long testId) {
        return resultRepository.resultTest(testId);
    }
}


