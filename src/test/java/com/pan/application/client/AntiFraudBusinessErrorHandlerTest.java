package com.pan.application.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pan.application.exception.ErrorResponse;
import com.pan.application.exception.impl.AntiFraudBusinessErrorException;
import com.pan.domain.resource.payload.MessageResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

@QuarkusTest
class AntiFraudBusinessErrorHandlerTest {

  private final String URI_STR = "http://localhost:8080/v1.0/transactions";

  AntiFraudBusinessErrorHandler handler;

  @InjectMock
  @Context
  UriInfo uriInfo;

  @Mock
  Response response;


  @BeforeEach
  public void init() throws URISyntaxException {
    this.uriInfo = mockUriInfo(URI_STR);

    this.response = Response.created(new URI(URI_STR)) //
        .status(404, "error-code-01") //
        .entity(new MessageResponse("Message Error")) //
        .build();

    this.handler = new AntiFraudBusinessErrorHandler();
    this.handler.uriInfo = this.uriInfo;
  }

  /**
   * Testando: AntiFraudBusinessErrorHandler.handles <br>
   * <p>
   * Cenário: Quando o stauts code é maior que 400 e menor que 500 então retorna TRUE.
   */
  @Test
  @SneakyThrows
  public void testCase_handles_01() {
    assertTrue(handler.handles(404, null));
  }

  /**
   * Testando: AntiFraudBusinessErrorHandler.handles <br>
   * <p>
   * Cenário: Quando o stauts code é maior que 500 então retorna FALSE.
   */
  @Test
  @SneakyThrows
  public void testCase_handles_02() {
    assertFalse(handler.handles(503, null));
  }

  /**
   * Testando: AntiFraudBusinessErrorHandler.handles <br>
   * <p>
   * Cenário: Quando o stauts code é menor que
   * <p>
   * 400 então retorna FALSE.
   */
  @Test
  @SneakyThrows
  public void testCase_handles_03() {
    assertFalse(handler.handles(200, null));
  }

  /**
   * Testando: AntiFraudBusinessErrorHandler.toThrowable <br>
   * <p>
   * Cenário: Quando falha na criação da exception então retorna null.
   */
  @Test
  @SneakyThrows
  public void testCase_toThrowable_01() {
    Response response = mock(Response.class);
    when(response.readEntity(ErrorResponse.class)).thenThrow(new NullPointerException("error"));

    assertDoesNotThrow(() -> {
      AntiFraudBusinessErrorException ex = handler.toThrowable(response);
      assertNull(ex);
    });
  }

  /**
   * Testando: AntiFraudBusinessErrorHandler.toThrowable <br>
   * <p>
   * Cenário: Quando consegue recuperar o body da requisição então preenche os dados da exception.
   */
  @Test
  @SneakyThrows
  public void testCase_toThrowable_02() {
    assertDoesNotThrow(() -> {
      AntiFraudBusinessErrorException ex = handler.toThrowable(response);

      assertEquals(ex.getCode(), "error-code-01");
      assertEquals(ex.getStatus(), 404);
      assertEquals(ex.getMessage(), "Message Error");
    });
  }

  private UriInfo mockUriInfo(String uri) throws URISyntaxException {
    UriInfo uriInfo = Mockito.mock(UriInfo.class);
    Mockito.when(uriInfo.getRequestUri()).thenReturn(new URI(uri));
    return uriInfo;
  }


}