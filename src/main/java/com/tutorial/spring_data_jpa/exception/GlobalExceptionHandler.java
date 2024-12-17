package com.tutorial.spring_data_jpa.exception;

import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String VALIDATION_FAILED = "Validation failed!";
    private static final String UNDEFINED_ERROR = "Undefined error!";
    private static final String GENERAL_EXCEPTION_MESSAGE = "An unexpected error occurred!";


    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponsePayload<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() == null ? UNDEFINED_ERROR : fieldError.getDefaultMessage()
                ));

        return new ResponsePayload.Builder<Map<String, String>>()
                .isSuccess(false)
                .message(VALIDATION_FAILED)
                .data(errors)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .showNotification(true)
                .build();
    }


    // Constraint Violation exceptions
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponsePayload<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {

        Map<String,String> errors =   ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation-> violation.getPropertyPath().toString(),
                        violation-> violation.getMessage() == null ? UNDEFINED_ERROR : violation.getMessage()
                ));


        return new ResponsePayload.Builder<Map<String, String>>()
                .isSuccess(false)
                .message(VALIDATION_FAILED)
                .data(errors)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .showNotification(true)
                .build();

    }

    // ResponseStatusException exceptions
    @ExceptionHandler(ResponseStatusException.class)
    public ResponsePayload<String> handleResponseStatusException(ResponseStatusException ex) {

        return new ResponsePayload.Builder<String>()
                .isSuccess(false)
                .message(ex.getReason() == null ? UNDEFINED_ERROR : ex.getReason())
                .statusCode(ex.getStatusCode().value())
                .showNotification(true)
                .build();

    }

    // General errors
    @ExceptionHandler(Exception.class)
    public ResponsePayload<String> handleGlobalExceptions(Exception ex, WebRequest request) {

        return new ResponsePayload.Builder<String>()
                .isSuccess(false)
                .message(ex.getMessage() == null ? GENERAL_EXCEPTION_MESSAGE : ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .showNotification(true)
                .build();
    }
}