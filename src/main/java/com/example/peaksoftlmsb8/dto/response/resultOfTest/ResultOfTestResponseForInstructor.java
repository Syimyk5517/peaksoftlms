package com.example.peaksoftlmsb8.dto.response.resultOfTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultOfTestResponseForInstructor {
   private Long resultOfTestId;
   private String studentFullName;
   private int correctAnswers;
   private int wrongAnswers;

}
