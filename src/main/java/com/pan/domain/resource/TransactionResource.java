package com.pan.domain.resource;


import com.pan.application.exception.BusinessValidator;
import com.pan.application.exception.impl.AntiFraudBusinessErrorException;
import com.pan.application.exception.impl.ParameterViolationException;
import com.pan.application.util.ResponseUtility;
import com.pan.domain.resource.payload.TransactionRequest;
import com.pan.domain.service.TransactionService;
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

@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Transactions", description = "Credit and Debit transaction validation")
public class TransactionResource implements ResponseUtility {

  @Inject
  TransactionService transactionService;

  @Inject
  Validator validator;

  @Operation(summary = "Credit validation")
  @APIResponses({@APIResponse(responseCode = "200", description = "validate succes"),
      @APIResponse(responseCode = "412", description = "Payload is not valid"),
      @APIResponse(responseCode = "400", description = "Bad request"),
      @APIResponse(responseCode = "500", description = "Internal server error")})
  @POST
  @Path("/credit")
  public Response creditValidate(@RequestBody(required = true) @Valid TransactionRequest request)
      throws ParameterViolationException, AntiFraudBusinessErrorException {

    BusinessValidator.validate(validator, request);
    return ok(transactionService.credit(request));
  }

  @Operation(summary = "Credit validation")
  @APIResponses({@APIResponse(responseCode = "200", description = "validate succes"),
      @APIResponse(responseCode = "412", description = "Payload is not valid"),
      @APIResponse(responseCode = "400", description = "Bad request"),
      @APIResponse(responseCode = "500", description = "Internal server error")})
  @POST
  @Path("/debit")
  public Response debitValidate(@RequestBody(required = true) @Valid TransactionRequest request)
      throws AntiFraudBusinessErrorException, ParameterViolationException {

    BusinessValidator.validate(validator, request);
    return ok(transactionService.debit(request));
  }

}
