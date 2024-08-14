package com.pan.application.client;

import com.pan.application.exception.impl.AntiFraudBusinessErrorException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class AntiFraudClientService {

  @Inject
  @RestClient
  AntiFraudClient client;

  /**
   * Valida transação de crédito.
   *
   * @param request objeto para validação.
   * @return Retorna uma mensagem.
   * @throws AntiFraudBusinessErrorException error de retorno do cliente
   */
  public Object creditTransaction(Object request) throws AntiFraudBusinessErrorException {
    return this.client.creditTransaction(request);
  }

  /**
   * Valida transação de débito.
   *
   * @param request objeto para validação
   * @return Retorna uma mensagem
   * @throws AntiFraudBusinessErrorException error de retorno do cliente
   */
  public Object debitTransaction(Object request) throws AntiFraudBusinessErrorException {
    return this.client.debitTransaction(request);
  }


  /**
   * Informa a situação sistema antifraud do credito
   *
   * @param request objeto de envio
   * @return Retorna mensagem
   */
  public Object creditAdvice(Object request) {
    log.info("RECEIVED CREDIT ADVICE PAYLOAD={}", request.toString());
    return this.client.creditAdvice(request);
  }


  /**
   * Informa a situação sistema antifraud do debito
   *
   * @param request objeto de envio
   * @return Retorno da mensagem
   */
  public Object debitAdvice(Object request) {
    log.info("RECEIVED DEBIT ADVICE PAYLOAD={}", request.toString());
    return this.client.debitAdvice(request);
  }

}
