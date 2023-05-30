package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassQuestionRequest;
import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassTestRequest;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.*;
import com.example.peaksoftlmsb8.repository.OptionRepository;
import com.example.peaksoftlmsb8.repository.QuestionRepository;
import com.example.peaksoftlmsb8.repository.ResultOfTestRepository;
import com.example.peaksoftlmsb8.repository.TestRepository;
import com.example.peaksoftlmsb8.service.ResultOfTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultOfTestServiceImpl implements ResultOfTestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final ResultOfTestRepository resultOfTestRepository;
    private final JwtService jwtService;

    @Override
    public List<ResultOfTestResponse> getAll() {
        User accountInToken = jwtService.getAccountInToken();
        Student student = accountInToken.getStudent();
        List<ResultOfTest> allByStudentId = resultOfTestRepository.findAllByStudentId(student.getId());
        List<ResultOfTestResponse> resultOfTestResponses = new ArrayList<>();
        for (ResultOfTest resultTest : allByStudentId) {
            int countCorrectAnswers = 0;
            Test test = resultTest.getTest();
            List<ResultQuestionResponse> resultQuestionResponses = new ArrayList<>();
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
                }
                for (Long studentAnswers : resultTest.getStudentAnswers()) {
                    for (Option option : question.getOptions()) {
                        if (question.getOptionType().equals(OptionType.SINGLETON)) {
                            if (studentAnswers.equals(option.getId())) {
                                questionResponse.setPoint(10);
                                questionResponse.getStudentAnswers().add(option.getId());
                                countCorrectAnswers += 10;
                            }
                        } else {
                            if (studentAnswers.equals(option.getId())) {
                                questionResponse.setPoint(questionResponse.getPoint() + 5);
                                questionResponse.getStudentAnswers().add(option.getId());
                                countCorrectAnswers += 5;
                            }
                        }
                    }
                }
                questionResponse.setOptionResponses(optionResponses);
                resultQuestionResponses.add(questionResponse);
            }
            ResultOfTestResponse resultOfTestResponse = ResultOfTestResponse.builder()
                    .resultOfTestId(resultTest.getId())
                    .testId(test.getId())
                    .testName(test.getName())
                    .resultQuestionResponses(resultQuestionResponses)
                    .studentPoint(countCorrectAnswers)
                    .build();
            resultOfTestResponses.add(resultOfTestResponse);
        }

        return resultOfTestResponses;

    }

    @Override
    public ResultOfTestResponse passTest(PassTestRequest passTestRequest) {
        User accountInToken = jwtService.getAccountInToken();

        Student student = accountInToken.getStudent();

        ResultOfTest resultOfTest = new ResultOfTest();

        Test test = testRepository.findById(passTestRequest.getTestId()).orElseThrow(
                () -> new NotFoundException("Test with id : " + passTestRequest.getTestId() + " not found !"));

        List<ResultQuestionResponse> resultQuestionResponses = new ArrayList<>();

        int countCorrectAnswer = 0;
        int countCorrect = 0;
        int countInCorrect = 0;
        for (PassQuestionRequest questionRequest : passTestRequest.getPassQuestionRequest()) {
            Question question = questionRepository.findById(questionRequest.getQuestionId()).orElseThrow(
                    () -> new NotFoundException("Question with id : " + questionRequest.getQuestionId()));

            ResultQuestionResponse questionResponse = new ResultQuestionResponse();
            questionResponse.setQuestionId(question.getId());
            questionResponse.setQuestionName(question.getQuestionName());
            questionResponse.setOptionType(question.getOptionType());
            questionResponse.setStudentAnswers(questionRequest.getOptionId());

            resultOfTest.getStudentAnswers().addAll(questionRequest.getOptionId());

            List<ResultOptionResponse> optionResponses = new ArrayList<>();

            if (questionRequest.getOptionId().isEmpty()) {

                for (Option option : question.getOptions()) {
                    ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
                    resultOptionResponse.setOptionId(option.getId());
                    resultOptionResponse.setText(option.getText());
                    resultOptionResponse.setIsTrue(option.getIsTrue());
                    optionResponses.add(resultOptionResponse);
                }
            } else {
                for (Option theOptionIsInTheDatabase : question.getOptions()) {
                    ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
                    resultOptionResponse.setOptionId(theOptionIsInTheDatabase.getId());
                    resultOptionResponse.setText(theOptionIsInTheDatabase.getText());
                    resultOptionResponse.setIsTrue(theOptionIsInTheDatabase.getIsTrue());

                    for (Long optionId : questionRequest.getOptionId()) {
                        Option option = optionRepository.findById(optionId).orElseThrow(
                                () -> new NotFoundException("Option with id : " + optionId + " not found !"));


                        if (question.getOptionType().equals(OptionType.SINGLETON)) {
                            if (option.getIsTrue().equals(theOptionIsInTheDatabase.getIsTrue())) {
                                questionResponse.setPoint(10);
                                countCorrect++;
                                countCorrectAnswer += 10;
                            }
                        } else {
                            if (option.getIsTrue().equals(theOptionIsInTheDatabase.getIsTrue())) {
                                questionResponse.setPoint(questionResponse.getPoint() + 5);
                                countCorrect++;
                                countCorrectAnswer += 5;
                            }
                        }

                    }
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

        return ResultOfTestResponse.builder()
                .testId(test.getId())
                .testName(test.getName())
                .resultQuestionResponses(resultQuestionResponses)
                .studentPoint(countCorrectAnswer)
                .build();

    }


}