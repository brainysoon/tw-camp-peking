package com.example.peking.controller;

import com.example.peking.exception.BusinessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerSupport {

    @ExceptionHandler(BusinessException.class)
    HttpEntity<String> handleBusinessException(BusinessException exception) {

        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}
