package com.pan.domain.service;

import com.pan.application.client.AntiFraudClientService;
import com.pan.domain.adapter.ValidationAdapter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AdviceService implements ValidationAdapter<Object> {

  @Inject
  AntiFraudClientService antiFraudClientService;

  @Override
  public Object credit(Object payload) {
    return antiFraudClientService.creditAdvice(payload);
  }

  @Override
  public Object debit(Object payload) {
    return antiFraudClientService.debitAdvice(payload);
  }
}
