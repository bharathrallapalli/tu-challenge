package com.tu.employee.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class EmployeeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleBadRequests(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, ErrorResponse.builder().code("INVALID_DATA").value(ex.getMessage()).build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {MissingRequestHeaderException.class})
    protected ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, ErrorResponse.builder().code("INVALID_DATA").value(ex.getMessage()).build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(Arrays.toString(ex.getDetailMessageArguments()));
        return handleExceptionInternal(ex,
                ErrorResponse.builder().code("INVALID_DATA").value(Arrays.toString(ex.getDetailMessageArguments())).build(),
                headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(Arrays.toString(ex.getDetailMessageArguments()));
        return handleExceptionInternal(ex,
                ErrorResponse.builder().code("INVALID_DATA").value(Arrays.toString(ex.getDetailMessageArguments())).build(),
                headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var message = ex.getMessage();
        if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof DateTimeParseException) {
            message = "dateOfBirth should be of yyyy-MM-dd format";
        }

        return handleExceptionInternal(ex,
                ErrorResponse.builder().code("INVALID_DATA").value(message).build(),
                headers, HttpStatus.BAD_REQUEST, request);
    }
}
