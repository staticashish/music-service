package com.challenge.plugsurfing.exception;

import java.util.Date;
import lombok.Getter;

@Getter
public class ErrorDetails {

  private Date timestamp;
  private String message;
  private String details;
  private Integer status;

  public ErrorDetails(Date timestamp, String message, String details, Integer status) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
    this.status = status;
  }
}
