package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.OptionType;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassQuestionRequest;
import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassTestRequest;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponse;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOptionResponse;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultQuestionResponse;
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
    public ResultOfTestResponse passTest(PassTestRequest passTestRequest) {
        User accountInToken = jwtService.getAccountInToken();

        Student student = accountInToken.getStudent();

        Test test = testRepository.findById(passTestRequest.getTestId()).orElseThrow(
                () -> new NotFoundException("Test with id : " + passTestRequest.getTestId() + " not found !"));

        List<ResultQuestionResponse> resultQuestionResponses = new ArrayList<>();

        int countCorrectAnswer = 0;
        int countCorrect = 0;
        int countInCorrect = 0;

        for (PassQuestionRequest passQuestionRequest : passTestRequest.getPassQuestionRequest()) {

            Question question = questionRepository.findById(passQuestionRequest.getQuestionId()).orElseThrow(
                    () -> new NotFoundException("Question with id : " + passQuestionRequest.getQuestionId()));

            ResultQuestionResponse resultQuestionResponse = new ResultQuestionResponse();
            resultQuestionResponse.setQuestionId(question.getId());
            resultQuestionResponse.setQuestionName(question.getQuestionName());
            resultQuestionResponse.setOptionType(question.getOptionType());


            List<ResultOptionResponse> resultOptionResponses = new ArrayList<>();

            if (passQuestionRequest.getOptionId().isEmpty()) {

                for (Option option : question.getOptions()) {
                    ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
                    resultOptionResponse.setOptionId(option.getId());
                    resultOptionResponse.setText(option.getText());
                    resultOptionResponse.setIsTrue(option.getIsTrue());
                    resultOptionResponse.setStudentAnswer(false);
                    resultOptionResponses.add(resultOptionResponse);
                }

            } else {

                for (Long optionId : passQuestionRequest.getOptionId()) {

                    Option option = optionRepository.findById(optionId).orElseThrow(
                            () -> new NotFoundException("Option with id : " + optionId + " not found !"));

                    for (Option theOptionIsInTheDatabase : question.getOptions()) {

                        if (question.getOptionType().equals(OptionType.SINGLETON)) {

                            ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
                            resultOptionResponse.setOptionId(theOptionIsInTheDatabase.getId());
                            resultOptionResponse.setText(theOptionIsInTheDatabase.getText());
                            resultOptionResponse.setIsTrue(theOptionIsInTheDatabase.getIsTrue());
                            resultOptionResponse.setStudentAnswer(option.getIsTrue());

                            if (option.getIsTrue().equals(theOptionIsInTheDatabase.getIsTrue())) {
                                resultQuestionResponse.setPoint(10);
                                countCorrect++;
                                countCorrectAnswer += 10;
                            } else {
                                countInCorrect++;
                            }
                        } else {
                            ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
                            resultOptionResponse.setOptionId(theOptionIsInTheDatabase.getId());
                            resultOptionResponse.setText(theOptionIsInTheDatabase.getText());
                            resultOptionResponse.setIsTrue(theOptionIsInTheDatabase.getIsTrue());
                            resultOptionResponse.setStudentAnswer(option.getIsTrue());

                            if (option.getIsTrue().equals(theOptionIsInTheDatabase.getIsTrue())) {
                                resultQuestionResponse.setPoint(resultQuestionResponse.getPoint() + 5);
                                countCorrect++;
                                countCorrectAnswer += 5;
                            } else {
                                countInCorrect++;

                            }
                        }

                    }
                }
            }
            resultQuestionResponse.setOptionResponses(resultOptionResponses);
            resultQuestionResponses.add(resultQuestionResponse);
        }
        ResultOfTest resultOfTest = new ResultOfTest();
        resultOfTest.setTest(test);
        resultOfTest.setStudent(student);
        resultOfTest.setCountCorrect(countCorrect);
        resultOfTest.setCountInCorrect(countInCorrect);
        resultOfTestRepository.save(resultOfTest);

        return ResultOfTestResponse.builder()
                .testId(test.getId())
                .testName(test.getName())
                .allPoints(2)
                .resultQuestionResponses(resultQuestionResponses)
                .studentPoint(countCorrectAnswer)
                .build();

    }

    @Override
    public List<ResultOfTestResponse> findAllTests() {
        return null;
    }


}