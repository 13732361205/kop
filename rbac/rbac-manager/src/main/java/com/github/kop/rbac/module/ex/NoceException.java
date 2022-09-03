package com.github.kop.rbac.module.ex;

import com.github.kop.rbac.module.enums.AppHttpCodeEnum;

public class NoceException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private AppHttpCodeEnum enums;
  private String message;
  private int code = 3500;

  public NoceException(String msg) {
    super(msg);
    this.message = msg;
  }

  public NoceException(String msg, Throwable e) {
    super(msg, e);
    this.message = msg;
  }

  public NoceException(String msg, int code) {
    super(msg);
    this.message = msg;
    this.code = code;
  }

  public NoceException(String msg, int code, Throwable e) {
    super(msg, e);
    this.message = msg;
    this.code = code;
  }

  public NoceException(AppHttpCodeEnum enums, String message) {
    super(message);
    this.enums = enums;
    this.code = enums.getCode();
  }

  public NoceException(AppHttpCodeEnum enums) {
    this.message = enums.getMsg();
    this.code = enums.getCode();
    this.enums = enums;
  }

  public AppHttpCodeEnum getEnums() {
    return enums;
  }

  public void setEnums(AppHttpCodeEnum enums) {
    this.enums = enums;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
