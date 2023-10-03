package com.shop.userapi.config.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // This annotation allows us to handle exceptions across the whole application
public class ApiExceptionHandler {

  @ExceptionHandler(value = {ApiRequestException.class})
  public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {

    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    ApiException apiException = new ApiException(e.getMessage(), badRequest, LocalDateTime.now());

    return new ResponseEntity<>(apiException, badRequest);
  }
}
