package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
class MyControllerAdvice {
    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<ErrResponse> errHandler(NoResourceFoundException e, HttpServletRequest servletRequest){
        var formatter = DateTimeFormatter.ofPattern("'data' dd-MM-yyyy 'as' hh:mm:ss");
        var response  = new ErrResponse(
                e.getMessage(),
                formatter.format(LocalDateTime.now()),
                HttpStatus.NOT_FOUND.value(),
                servletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

