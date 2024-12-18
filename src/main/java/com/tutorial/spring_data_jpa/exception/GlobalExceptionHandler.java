package com.tutorial.spring_data_jpa.exception;

import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractResponsePayload {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponsePayload<Map<String, String>>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    ResponsePayload<Map<String, String>> response =
        ResponsePayload.error(ResponseEnum.BAD_REQUEST, MessageEnum.WRONG_EMAIL_FORMAT, true);
    response =
        new ResponsePayload.Builder<Map<String, String>>()
            .statusCode(ResponseEnum.BAD_REQUEST.getHttpStatusCode())
            .description(ResponseEnum.BAD_REQUEST.getDescription())
            .isSuccess(ResponseEnum.BAD_REQUEST.getSuccess())
            .data(errors)
            .showNotification(true)
            .build();

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
