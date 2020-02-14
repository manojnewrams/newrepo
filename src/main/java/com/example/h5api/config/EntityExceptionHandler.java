package com.example.h5api.config;

import com.example.h5api.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public void userAlreadyExistException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(CampaignIsClosedException.class)
    public void campaignIsClosedException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public void userNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

     @ExceptionHandler(GenericEmptyListException.class)
    public void genericEmptyListException(HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.NO_CONTENT.value());
     }

    @ExceptionHandler(GenericNotFoundException.class)
    public void genericNotFoundException(HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(GenericAlreadyExistException.class)
    public void GenericAlreadyExistException(HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.CONFLICT.value());
    }
}