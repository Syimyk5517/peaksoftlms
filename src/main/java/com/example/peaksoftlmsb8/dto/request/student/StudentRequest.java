package com.example.peaksoftlmsb8.dto.request.student;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.validation.phoneNumber.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    @NotBlank(message = "Имя не должно быть пустым")
    @NotNull(message = "Имя не должно быть пустым")
    private String firstName;
    @NotBlank(message = "Фамилия не должна быть пустым")
    @NotNull(message = "Фамилия не должна быть пустым")
    private String lastName;
    @NotBlank(message = "Номер телефона не должна быть пустым.")
    @NotNull(message = "Номер телефона не должна быть пустым.")
    @PhoneNumberValid(message = "Номер телефона не должна быть больше 13 цифр и должен начинаться с +996.")
    private String phoneNumber;
    @NotBlank(message = "Электронная почта не должна быть пустым.")
    @NotNull(message = "Электронная почта не должна быть пустым.")
    @Email(message = "Электронная почта должна содержать @ ")
    private String email;

    private Long groupId;
    private FormLearning formLearning;

    private String link;
}
//    User accountInToken = jwtService.getAccountInToken();
//    Student student = accountInToken.getStudent();
//    Test test = testRepository.findById(passTestRequest.getTestId())
//            .orElseThrow(() -> {
//                String errorMessage = "Test with id: " + passTestRequest.getTestId() + " not found!";
//                logger.error(errorMessage);
//                throw new NotFoundException(errorMessage);
//            });
//
//    ResultOfTest resultOfTest = new ResultOfTest();
//    List<ResultQuestionResponse> resultQuestionResponses = new ArrayList<>();
//    int countCorrectAnswer = 0;
//    int countCorrect = 0;
//    int countInCorrect = 0;
//
//for (PassQuestionRequest questionRequest : passTestRequest.getPassQuestionRequest()) {
//        Question question = questionRepository.findById(questionRequest.getQuestionId())
//        .orElseThrow(() -> {
//        String errorMessage = "Question with id: " + questionRequest.getQuestionId() + " not found";
//        logger.error(errorMessage);
//        throw new NotFoundException(errorMessage);
//        });
//
//        ResultQuestionResponse questionResponse = new ResultQuestionResponse();
//        questionResponse.setQuestionId(question.getId());
//        questionResponse.setQuestionName(question.getQuestionName());
//        questionResponse.setOptionType(question.getOptionType());
//        questionResponse.setStudentAnswers(questionRequest.getOptionId());
//
//        resultOfTest.getStudentAnswers().addAll(questionRequest.getOptionId());
//
//        List<ResultOptionResponse> optionResponses = new ArrayList<>();
//
//        if (questionRequest.getOptionId().isEmpty()) {
//        for (Option option : question.getOptions()) {
//        ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
//        resultOptionResponse.setOptionId(option.getId());
//        resultOptionResponse.setText(option.getText());
//        resultOptionResponse.setIsTrue(option.getIsTrue());
//        optionResponses.add(resultOptionResponse);
//        }
//        } else {
//        for (Option theOptionIsInTheDatabase : question.getOptions()) {
//        ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
//        resultOptionResponse.setOptionId(theOptionIsInTheDatabase.getId());
//        resultOptionResponse.setText(theOptionIsInTheDatabase.getText());
//        resultOptionResponse.setIsTrue(theOptionIsInTheDatabase.getIsTrue());
//
//        for (Long optionId : questionRequest.getOptionId()) {
//        Option option = optionRepository.findById(optionId)
//        .orElseThrow(() -> {
//        String errorMessage = "Option with id: " + optionId + " not found!";
//        logger.error(errorMessage);
//        throw new NotFoundException(errorMessage);
//        });
//
//        if (option.getIsTrue().equals(theOptionIsInTheDatabase.getIsTrue())) {
//        int pointsToAdd = (question.getOptionType() == OptionType.SINGLETON) ? 10 : 5;
//        questionResponse.setPoint(questionResponse.getPoint() + pointsToAdd);
//        countCorrect++;
//        countCorrectAnswer += pointsToAdd;
//        }
//        }
//        }
//        }
//
//        if (questionResponse.getPoint() == 0) {
//        countInCorrect++;
//        }
//
//        questionResponse.setOptionResponses(optionResponses);
//        resultQuestionResponses.add(questionResponse);
//        }
//
//        resultOfTest.setTest(test);
//        resultOfTest.setStudent(student);
//        resultOfTest.setCountCorrect(countCorrect);
//        resultOfTest.setCountInCorrect(countInCorrect);
//        resultOfTestRepository.save(resultOfTest);
//
//        return ResultOfTestResponseForStudent.builder()
//        .testId(test.getId())
//        .testName(test.getName())
//        .resultQuestionResponses(resultQuestionResponses)
//        .studentPoint(countCorrectAnswer)
//        .build();





//
//
//
//User accountInToken = jwtService.getAccountInToken();
//    Student student = accountInToken.getStudent();
//    Test test = testRepository.findById(passTestRequest.getTestId())
//            .orElseThrow(() -> new NotFoundException("Тест с идентификатором: " + passTestRequest.getTestId() + " не найден!"));
//
//    ResultOfTest resultOfTest = new ResultOfTest();
//    List<ResultQuestionResponse> resultQuestionResponses = new ArrayList<>();
//    int countCorrectAnswer = 0;
//    int countCorrect = 0;
//    int countInCorrect = 0;
//
//for (PassQuestionRequest questionRequest : passTestRequest.getPassQuestionRequest()) {
//        Question question = questionRepository.findById(questionRequest.getQuestionId())
//        .orElseThrow(() -> new NotFoundException("Вопрос с идентификатором: " + questionRequest.getQuestionId() + " не найден"));
//
//        ResultQuestionResponse questionResponse = new ResultQuestionResponse();
//        questionResponse.setQuestionId(question.getId());
//        questionResponse.setQuestionName(question.getQuestionName());
//        questionResponse.setOptionType(question.getOptionType());
//        questionResponse.setStudentAnswers(questionRequest.getOptionId());
//
//        resultOfTest.getStudentAnswers().addAll(questionRequest.getOptionId());
//
//        List<ResultOptionResponse> optionResponses = new ArrayList<>();
//
//        for (Option option : question.getOptions()) {
//        ResultOptionResponse resultOptionResponse = new ResultOptionResponse();
//        resultOptionResponse.setOptionId(option.getId());
//        resultOptionResponse.setText(option.getText());
//        resultOptionResponse.setIsTrue(option.getIsTrue());
//        optionResponses.add(resultOptionResponse);
//
//        if (questionRequest.getOptionId().contains(option.getId()) && option.getIsTrue().equals(question.getOptionType())) {
//        int pointsToAdd = (question.getOptionType() == OptionType.SINGLETON) ? 10 : 5;
//        questionResponse.setPoint(questionResponse.getPoint() + pointsToAdd);
//        countCorrect++;
//        countCorrectAnswer += pointsToAdd;
//        }
//        }
//
//        if (questionResponse.getPoint() == 0) {
//        countInCorrect++;
//        }
//
//        questionResponse.setOptionResponses(optionResponses);
//        resultQuestionResponses.add(questionResponse);
//        }
//
//        resultOfTest.setTest(test);
//        resultOfTest.setStudent(student);
//        resultOfTest.setCountCorrect(countCorrect);
//        resultOfTest.setCountInCorrect(countInCorrect);
//        resultOfTestRepository.save(resultOfTest);
//
//        return ResultOfTestResponseForStudent.builder()
//        .testId(test.getId())
//        .testName(test.getName())
//        .resultQuestionResponses(resultQuestionResponses)
//        .studentPoint(countCorrectAnswer)
//        .build();
