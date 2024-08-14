package com.pan.application.client;

import com.pan.application.exception.ErrorResponse;
import com.pan.application.exception.impl.AntiFraudBusinessErrorException;
import com.pan.domain.resource.payload.MessageResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Slf4j
@Provider
public class AntiFraudBusinessErrorHandler implements
    ResponseExceptionMapper<AntiFraudBusinessErrorException> {

  @Context
  UriInfo uriInfo;

  public AntiFraudBusinessErrorHandler() {
    super();
  }

  @Override
  public boolean handles(int statusCode, MultivaluedMap<String, Object> headers) {
    return statusCode >= 400 && statusCode < 500;
  }

  public AntiFraudBusinessErrorException toThrowable(Response response) {
    try {

      var errorResponse = ErrorResponse.builder() //
          .timestamp(LocalDateTime.now()) //
          .path(uriInfo.getRequestUri().getPath()) //
          .status(response.getStatus()) //
          .code(response.getStatusInfo().getReasonPhrase()) //
          .message(response.readEntity(MessageResponse.class).message()) //
          .build(); //

      return new AntiFraudBusinessErrorException(errorResponse);
    } catch (Exception e) {
      log.error("Error handling AntiFraudBusinessErrorException: {}", e.getLocalizedMessage());
      return null;
    }
  }

}
