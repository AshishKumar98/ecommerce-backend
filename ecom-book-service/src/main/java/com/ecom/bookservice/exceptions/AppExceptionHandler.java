package com.ecom.bookservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        ErrorMessage errMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage()==null ? ex.toString() : ex.getLocalizedMessage());
        log.error("Generic Exception: {}", ex);
        return new ResponseEntity<>(errMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorMessage errErrorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage()==null ? ex.toString() : ex.getLocalizedMessage());
        log.error("NullPointer Exception {}", ex);
        return new ResponseEntity<>(errErrorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

