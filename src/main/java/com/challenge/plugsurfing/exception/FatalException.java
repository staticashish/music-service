package com.challenge.plugsurfing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FatalException extends RuntimeException {

  public FatalException(String message) {
    super(message);
  }

  public FatalException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
