package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.TestRequest;
import com.example.peaksoftlmsb8.dto.request.TestUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponseForStudent;
import com.example.peaksoftlmsb8.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tests")
@RequiredArgsConstructor
@Tag(name = "Tests")
public class TestApi {
    private final TestService testService;

    @GetMapping
    @Operation(summary = "This method returns all tests",
            description = "Find all tests")
    public List<TestResponse> findAll() {
        return testService.findAll();
    }

    @PostMapping
    @Operation(summary = "Add test from  by instructor",
            description = "This method saved Tests")
    public SimpleResponse save(@RequestBody TestRequest testRequest) {
        return testService.saveTest(testRequest);
    }

    @GetMapping("/findById")
    @Operation(summary = "This method returns test by id from by instructor",
            description = "This method return test find by id ")
    public TestResponse findById(@RequestParam Long testId) {
        return testService.findById(testId);
    }

    @GetMapping("/findByTestById")
    @Operation(summary = "This method returns test by id from by instructor",
            description = "This method return test find by id ")
    public TestResponseForStudent findByTestId(@RequestParam Long testId){
        return testService.findByTestById(testId);
    }

    @PutMapping
    @Operation(summary = "This method updated test by id from by instructor",
            description = "This method updated test by id")
    public SimpleResponse updateTest(@RequestParam Long testId, @RequestBody TestUpdateRequest testUpdateRequest) {
        return testService.updateTest(testId, testUpdateRequest);
    }

    @DeleteMapping
    @Operation(summary = "This method deleted test by id from by instructor",
            description = "This method deleted test by id ")
    public SimpleResponse deleteById(@RequestParam Long testId) {
        return testService.deleteById(testId);
    }
}
