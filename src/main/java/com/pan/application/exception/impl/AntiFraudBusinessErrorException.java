package com.pan.application.exception.impl;

import static com.pan.application.util.CheckUtils.notEmpty;
import static com.pan.application.util.CheckUtils.notNull;

import com.pan.application.exception.BusinessException;
import com.pan.application.exception.ErrorResponse;
import com.pan.application.exception.Hidden;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;

@Getter
@RegisterForReflection
public class AntiFraudBusinessErrorException extends BusinessException {

  public static final String CODE = "exception.validate.businessError";
  public static final Integer STATUS = 412;

  private final ErrorResponse error;

  public AntiFraudBusinessErrorException(@Hidden ErrorResponse error) {
    super( //
        notNull(error) && notNull(error.getStatus()) ? error.getStatus() : STATUS, //
        notNull(error) && notEmpty(error.getCode()) ? error.getCode() : CODE, //
        notNull(error) && notEmpty(error.getMessage()) ? error.getMessage() : "no-message");

    this.error = error;

    if (notNull(error) && notEmpty(error.getParams())) {
      error.getParams().forEach(this::param);
    }
  }
}
