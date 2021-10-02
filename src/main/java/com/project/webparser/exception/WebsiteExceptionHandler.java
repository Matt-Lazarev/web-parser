package com.project.webparser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class WebsiteExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {

        String error = ex.getClass() +": "+ex.getMessage();
        ErrorMessage errorMessage = new ErrorMessage("ERROR", error, HttpStatus.BAD_REQUEST);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(WrongWebsiteUrlException.class)
    protected ResponseEntity<Object> handleWrongWebsiteUrlException(WrongWebsiteUrlException ex) {

        String error = ex.getClass() +": "+ex.getMessage();
        ErrorMessage errorMessage = new ErrorMessage("ERROR", error, HttpStatus.NOT_FOUND);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorMessage);
    }
}
