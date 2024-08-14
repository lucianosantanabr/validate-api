package com.pan.domain.resource;

import com.pan.application.exception.BusinessValidator;
import com.pan.application.exception.impl.ParameterViolationException;
import com.pan.application.message.ProduceMessage;
import com.pan.application.util.ResponseUtility;
import com.pan.domain.resource.payload.TransactionRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/advices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Advices", description = "Credit and Debit advices")
public class AdviceResource implements ResponseUtility {

  @Inject
  ProduceMessage produceMessage;

  @Inject
  Validator validator;

  @Operation(summary = "Credit")
  @APIResponses({@APIResponse(responseCode = "200", description = "validate succes"),
      @APIResponse(responseCode = "400", description = "Bad request"),
      @APIResponse(responseCode = "500", description = "Internal server error")})
  @POST
  @Path("/credit")
  public Response credit(@RequestBody(required = true) @Valid TransactionRequest request)
      throws ParameterViolationException {

    BusinessValidator.validate(validator, request);
    this.produceMessage.sendMessageCreditAdvice(request);
    return ok();
  }

  @Operation(summary = "Debit")
  @APIResponses({@APIResponse(responseCode = "200", description = "validate succes"),
      @APIResponse(responseCode = "400", description = "Bad request"),
      @APIResponse(responseCode = "500", description = "Internal server error")})
  @POST
  @Path("/debit")
  public Response debit(@RequestBody(required = true) @Valid TransactionRequest request)
      throws ParameterViolationException {

    BusinessValidator.validate(validator, request);
    this.produceMessage.sendMessageDebitAdvice(request);
    return ok();
  }
}
