package com.example.api.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;


@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvicer {

    @ExceptionHandler(com.example.api.exceptions.handleException.class)
    public ErrorCode handleMyException(com.example.api.exceptions.handleException mex) {
        return new ErrorCode(mex.getStatus(), mex.getMessage());
    }
}
