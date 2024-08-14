package com.pan.domain.service;

import com.pan.application.client.AntiFraudClientService;
import com.pan.application.exception.impl.AntiFraudBusinessErrorException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class TransactionServiceTest {

  @InjectMock
  AntiFraudClientService antiFraudClientService;

  @Inject
  TransactionService transactionService;

  @Test
  void shouldCallCredit() throws AntiFraudBusinessErrorException {
    Mockito.when(antiFraudClientService.creditTransaction(Mockito.any()))
        .thenReturn("message credit");
    var expected = transactionService.credit("message");
    Assertions.assertEquals("message credit", expected);

  }

  @Test
  void shouldCallDebit() throws AntiFraudBusinessErrorException {
    Mockito.when(antiFraudClientService.debitTransaction(Mockito.any()))
        .thenReturn("message debit");
    var expected = transactionService.debit("message");
    Assertions.assertEquals("message debit", expected);
  }
}