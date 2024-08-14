package com.pan.domain.adapter;

import com.pan.application.exception.impl.AntiFraudBusinessErrorException;

public interface ValidationAdapter<T> {

  T credit(Object object) throws AntiFraudBusinessErrorException;

  T debit(Object object) throws AntiFraudBusinessErrorException;

}
