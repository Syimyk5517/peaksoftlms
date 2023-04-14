package com.example.peaksoftlmsb8.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
@Data
@Builder
@AllArgsConstructor
public class SimpleResponse {
    private HttpStatus httpStatus;
    private String message;
}
