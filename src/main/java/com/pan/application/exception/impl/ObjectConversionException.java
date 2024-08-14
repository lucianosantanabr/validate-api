package com.pan.application.exception.impl;

import com.pan.application.exception.BusinessException;
import java.io.Serial;

public class ObjectConversionException extends BusinessException {

  @Serial
  private static final long serialVersionUID = 3030719260677144636L;

  public static final String CODE = "exception.conversion.error";
  public static final Integer STATUS = 412;
  public static final String MESSAGE = "Could not convert object. className:[[className]] - payload:[[payload]]";

  public ObjectConversionException(String className, Object payload) {
    super(STATUS, CODE, MESSAGE);
    param("className", className);
    param("payload", payload);
    warn();
  }

}
