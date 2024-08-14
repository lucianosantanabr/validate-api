package com.pan.application.message;

import com.pan.application.exception.impl.ObjectConversionException;
import com.pan.application.util.mapper.ObjectConverter;
import com.pan.domain.resource.payload.TransactionRequest;
import com.pan.domain.service.AdviceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Slf4j
public class ConsumerMessage {

  @Inject
  ObjectConverter converter;

  @Inject
  AdviceService adviceService;

  /**
   * Consome as mensagens da fila credito advice
   *
   * @param message objeto recebido
   * @throws ObjectConversionException erro de conversão de objeto.
   */
  @Incoming("credit")
  public void listenerCreditAdvide(Object message) throws ObjectConversionException {
    var transactionRequest = converteMessage(message);
    adviceService.credit(transactionRequest);
  }

  /**
   * Consome as mensagens da fila débito advice
   *
   * @param message objeto recebido
   * @throws ObjectConversionException erro de conversão de objeto.
   */
  @Incoming("debit")
  public void listenerDebitAdvide(Object message) throws ObjectConversionException {
    TransactionRequest transactionRequest = converteMessage(message);
    adviceService.debit(transactionRequest);
  }

  /**
   * Conversão do objeto recebido da mensagem
   *
   * @param message objeto recebido.
   * @return objeto transaction
   * @throws ObjectConversionException erro de conversão de objeto.
   */
  private TransactionRequest converteMessage(Object message) throws ObjectConversionException {
    return converter.toObject(message, TransactionRequest.class);
  }

}
