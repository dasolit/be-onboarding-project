package com.fc.core.excption;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
  private String code;
  private String msg;

  public BaseException(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
