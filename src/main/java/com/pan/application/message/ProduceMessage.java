package com.pan.application.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class ProduceMessage {

  @Channel("credit-advice")
  Emitter<Object> sendCreditAdvice;

  @Channel("debit-advice")
  Emitter<Object> sendDebitAdvice;

  /**
   * Envia mensagem do objeto credit advice para fila
   *
   * @param payload objeto de envio
   */
  public void sendMessageCreditAdvice(Object payload) {
    sendCreditAdvice.send(payload);
  }

  /**
   * Envia mensagem do objeto debit advice para fila
   *
   * @param payload objeto de envio
   */
  public void sendMessageDebitAdvice(Object payload) {
    sendDebitAdvice.send(payload);
  }

}
