package com.virtuslab.internship.application.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.virtuslab.internship.domain.exceptions.RequestNotSatisfiedException;
import com.virtuslab.internship.domain.exceptions.EntityNotFoundException;


@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class, NullPointerException.class, RequestNotSatisfiedException.class,
            EntityNotFoundException.class })
    protected ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
