package com.example.peking.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private HttpStatus httpStatus;

    public BusinessException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }


    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}