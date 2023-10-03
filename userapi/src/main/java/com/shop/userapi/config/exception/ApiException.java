package com.shop.userapi.config.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApiException {
  private final String message;
  private final HttpStatus httpStatus;
  private final LocalDateTime timestamp;
}
