package com.pan.domain.service;

import com.pan.application.client.AntiFraudClientService;
import com.pan.application.exception.impl.AntiFraudBusinessErrorException;
import com.pan.domain.adapter.ValidationAdapter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TransactionService implements ValidationAdapter<Object> {

  @Inject
  AntiFraudClientService antiFraudClientService;

  @Override
  public Object credit(Object payload) throws AntiFraudBusinessErrorException {
    return antiFraudClientService.creditTransaction(payload);
  }

  @Override
  public Object debit(Object payload) throws AntiFraudBusinessErrorException {
    return antiFraudClientService.debitTransaction(payload);
  }
}
