package com.example.peaksoftlmsb8.db.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlReadyExistException extends RuntimeException{
    public AlReadyExistException() {
    }

    public AlReadyExistException(String message) {
        super(message);
    }
}
