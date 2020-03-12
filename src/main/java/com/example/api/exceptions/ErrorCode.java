package com.example.api.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorCode {

    private HttpStatus errorCode;
    private String message;

    public ErrorCode(HttpStatus errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

}
