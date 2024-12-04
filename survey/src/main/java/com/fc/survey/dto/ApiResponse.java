package com.fc.survey.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
  private final HttpStatus code;
  private final String msg;
  private final T data;

  public ApiResponse(HttpStatus code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

}
