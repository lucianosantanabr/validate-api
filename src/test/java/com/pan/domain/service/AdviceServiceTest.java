package com.pan.domain.service;

import com.pan.application.client.AntiFraudClientService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class AdviceServiceTest {


  @InjectMock
  AntiFraudClientService antiFraudClientService;

  @Inject
  AdviceService adviceService;

  @Test
  void shouldCallCredit() {
    Mockito.when(antiFraudClientService.creditAdvice(Mockito.any())).thenReturn("message credit");
    var expected = adviceService.credit("message");
    Assertions.assertEquals("message credit", expected);
  }

  @Test
  void shouldCallDebit() {
    Mockito.when(antiFraudClientService.debitAdvice(Mockito.any())).thenReturn("message debit");
    var expected = adviceService.debit("message");
    Assertions.assertEquals("message debit", expected);
  }
}