package com.example.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class handleException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1082638792299473988L;
    private HttpStatus status;
    private String message;

    public handleException() {
    }

    public handleException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
