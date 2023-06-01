package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassTestRequest;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForStudent;
import com.example.peaksoftlmsb8.service.ResultOfTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/results")
@RequiredArgsConstructor
@Tag(name = "Result of test")
public class ResultOfTestApi {
 private final ResultOfTestService resultOfTestService;

    @GetMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @Operation(summary = "This method takes a test id as a parameter from the instructor to check the student's test result",
            description = "this method returns the test result for the instructor")
    public List<ResultOfTestResponseForInstructor> findAllResultOfTest(@RequestParam Long testId) {
        return resultOfTestService.findAll(testId);
    }
    @GetMapping("/testId")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "This method returns the result of the test submitted by the student and takes the test id as a parameter",
            description = "this method returns the test result for the student")
    public ResultOfTestResponseForStudent findResultOfTestByTestIdForStudent (@RequestParam Long testId) {
        return resultOfTestService.findResultOfTestByTestIdForStudent(testId);
    }
     @PostMapping
     @PreAuthorize("hasRole('STUDENT')")
     @Operation(summary = "This method is that the student takes the test",
                 description = "This method is that the student takes the test")
    public ResultOfTestResponseForStudent passTest(@RequestParam PassTestRequest passTestRequest){
        return resultOfTestService.passTest(passTestRequest);
     }
}
