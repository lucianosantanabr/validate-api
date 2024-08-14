package com.pan.application.client;


import com.pan.application.exception.impl.AntiFraudBusinessErrorException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "antifraud")
@RegisterProvider(AntiFraudBusinessErrorHandler.class)
public interface AntiFraudClient {

  @POST
  @Path("/credit/transaction")
  Object creditTransaction(Object payload) throws AntiFraudBusinessErrorException;

  @POST
  @Path("/debit/transaction")
  Object debitTransaction(Object payload) throws AntiFraudBusinessErrorException;

  @POST
  @Path("/credit/advice")
  Object creditAdvice(Object payload);

  @POST
  @Path("/debit/advice")
  Object debitAdvice(Object payload);

}
